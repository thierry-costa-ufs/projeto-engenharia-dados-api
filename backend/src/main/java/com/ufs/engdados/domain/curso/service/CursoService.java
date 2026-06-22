package com.ufs.engdados.domain.curso.service;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.event.CursoDeletadoEvent;
import com.ufs.engdados.domain.curso.event.CursoSalvoEvent;
import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import com.ufs.engdados.domain.curso.model.relational.Curso;
import com.ufs.engdados.domain.curso.mapper.CursoMapper;
import com.ufs.engdados.domain.curso.repository.nosql.CursoNoSqlRepository;
import com.ufs.engdados.domain.curso.repository.relational.CursoRelationalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRelationalRepository relationalRepository;
    private final CursoNoSqlRepository noSqlRepository;
    private final CursoMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public CursoService(CursoRelationalRepository relationalRepository,
                        CursoNoSqlRepository noSqlRepository,
                        CursoMapper mapper,
                        ApplicationEventPublisher eventPublisher) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CursoDTO.Response create(CursoDTO.Request dto) {
        Curso relational = mapper.toEntity(dto);
        relational = relationalRepository.save(relational);

        eventPublisher.publishEvent(new CursoSalvoEvent(relational.getIdCurso(), dto));

        return mapper.toResponse(relational);
    }

    @Transactional(readOnly = true)
    public Page<CursoDTO.Response> findAllRelational(Pageable pageable) {
        Page<Curso> cursosPg = relationalRepository.findAll(pageable);

        List<Integer> ids = cursosPg.getContent().stream().map(Curso::getIdCurso).toList();

        List<CursoDocument> documentos = noSqlRepository.findByIdCursoIn(ids);

        Map<Integer, CursoDocument> mongoMap = documentos.stream()
                .collect(Collectors.toMap(CursoDocument::getIdCurso, doc -> doc));

        return cursosPg.map(curso -> {
            CursoDocument doc = mongoMap.get(curso.getIdCurso());
            if (doc != null) {
                return mapper.toResponse(curso, "INTEGRADO_NOSQL");
            }
            return mapper.toResponse(curso);
        });
    }

    @Transactional(readOnly = true)
    public Page<CursoDTO.Response> findAllNoSql(Pageable pageable) {
        return noSqlRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional
    public CursoDTO.Response update(Integer id, CursoDTO.Request dto) {
        Curso relational = relationalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado na base relacional ID: " + id));

        mapper.updateEntityFromDto(dto, relational);
        Curso cursoAtualizado = relationalRepository.save(relational);

        eventPublisher.publishEvent(new CursoSalvoEvent(cursoAtualizado.getIdCurso(), dto));

        return mapper.toResponse(cursoAtualizado);
    }

    @Transactional
    public void delete(Integer id) {
        if (!relationalRepository.existsById(id)) {
            throw new EntityNotFoundException("Curso inexistente.");
        }
        relationalRepository.deleteById(id);

        eventPublisher.publishEvent(new CursoDeletadoEvent(id));
    }

}