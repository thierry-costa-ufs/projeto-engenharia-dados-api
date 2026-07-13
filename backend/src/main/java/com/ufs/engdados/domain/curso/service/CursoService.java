package com.ufs.engdados.domain.curso.service;

import com.ufs.engdados.domain.curso.dto.CursoDTO;
import com.ufs.engdados.domain.curso.model.nosql.CursoDocument;
import com.ufs.engdados.domain.curso.model.relational.Curso;
import com.ufs.engdados.domain.curso.mapper.CursoMapper;
import com.ufs.engdados.domain.curso.repository.nosql.CursoNoSqlRepository;
import com.ufs.engdados.domain.curso.repository.relational.CursoRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    private final CursoRelationalRepository relationalRepository;
    private final CursoNoSqlRepository noSqlRepository;

    private final EntityManager entityManager;

    public CursoService(CursoRelationalRepository relationalRepository,
                        CursoNoSqlRepository noSqlRepository,
                        EntityManager entityManager) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    @Transactional
    public void consertarContadorDaAWS() {
        try {
            String sql = "SELECT setval(pg_get_serial_sequence('universidade.curso', 'idcurso'), COALESCE((SELECT MAX(idcurso) FROM universidade.curso), 1))";
            entityManager.createNativeQuery(sql).getSingleResult();
            System.out.println("Contador do PostgreSQL sincronizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível sincronizar o contador. " + e.getMessage());
        }
    }

    @Transactional
    public CursoDTO.Response create(CursoDTO.Request dto) {
        Curso cursoSalvoRelacional = relationalRepository.save(CursoMapper.toEntity(dto));

        CursoDocument documentoNoSql = CursoMapper.toDocument(dto);

        documentoNoSql.setIdCurso(cursoSalvoRelacional.getIdCurso());

        CursoDocument salvoNoSql = noSqlRepository.save(documentoNoSql);

        return CursoMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<CursoDTO.Response> findAllRelational(Pageable pageable) {
        Page<Curso> cursosPg = relationalRepository.findAll(pageable);
        return cursosPg.map(curso -> CursoMapper.toResponse(curso));
    }

    public Page<CursoDTO.Response> findAllNoSql(Pageable pageable) {
        Page<CursoDocument> cursosPg = noSqlRepository.findAll(pageable);
        return cursosPg.map(curso -> CursoMapper.toResponse(curso));
    }

    @Transactional
    public CursoDTO.Response update(Integer id, CursoDTO.Request dto) {
        if (dto.idCurso() != null && !id.equals(dto.idCurso())) {
            throw new IllegalArgumentException("O id do curso da URL não corresponde ao id do curso enviado no corpo da requisição.");
        }

        Curso relational = relationalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado na base relacional ID: " + id));

        CursoMapper.updateEntity(dto, relational);
        relationalRepository.save(relational);

        CursoDocument nosql = noSqlRepository.findByIdCurso(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado na base NoSQL ID: " + id));

        CursoMapper.updateDocument(dto, nosql);
        CursoDocument salvoNoSql = noSqlRepository.save(nosql);

        return CursoMapper.toResponse(salvoNoSql);
    }

    @Transactional
    public void delete(Integer id) {
        relationalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado na base relacional ID: " + id));
        relationalRepository.deleteById(id);

        noSqlRepository.findByIdCurso(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado na base NoSQL ID: " + id));
        noSqlRepository.deleteByIdCurso(id);
    }
}