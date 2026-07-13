package com.ufs.engdados.domain.turma.service;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.mapper.TurmaMapper;
import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import com.ufs.engdados.domain.turma.repository.nosql.TurmaNoSqlRepository;
import com.ufs.engdados.domain.turma.repository.relational.TurmaRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaService {

    private final TurmaRelationalRepository relationalRepository;
    private final TurmaNoSqlRepository noSqlRepository;
    private final EntityManager entityManager;

    public TurmaService(TurmaRelationalRepository relationalRepository,
                        TurmaNoSqlRepository noSqlRepository,
                        EntityManager entityManager) {
        this.relationalRepository = relationalRepository;
        this.noSqlRepository = noSqlRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public TurmaDTO.Response create(TurmaDTO.Request request) {
        Disciplina disciplina = null;
        if (request.codDisc() != null) {
            disciplina = entityManager.getReference(Disciplina.class, request.codDisc());
        }

        Turma entity = TurmaMapper.toEntity(request, disciplina);
        entity = relationalRepository.save(entity);

        // força o PostgreSQL a gravar e gerar o ID imediatamente
        entityManager.flush();

        TurmaDocument document = TurmaMapper.toDocument(request, entity.getIdTurma());
        TurmaDocument salvoNoSql = noSqlRepository.save(document);

        return TurmaMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<TurmaDTO.Response> findAllRelational(Pageable pageable) {
        Page<Turma> turmas = relationalRepository.findAll(pageable);
        return turmas.map(TurmaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TurmaDTO.Response> findAllNoSql(Pageable pageable) {
        Page<TurmaDocument> turmas = noSqlRepository.findAll(pageable);
        return turmas.map(TurmaMapper::toResponse);
    }

    @Transactional
    public TurmaDTO.Response update(Integer idTurma, TurmaDTO.Request request) {
        Turma entity = relationalRepository.findById(idTurma)
                .orElseThrow(() -> new ResourceNotFoundException("Turma " + idTurma + " não encontrada"));

        Disciplina disciplina = null;
        if (request.codDisc() != null) {
            disciplina = entityManager.getReference(Disciplina.class, request.codDisc());
        }

        TurmaMapper.updateEntity(request, entity, disciplina);
        relationalRepository.save(entity);

        TurmaDocument document = noSqlRepository.findById(idTurma)
                .orElseThrow(() -> new ResourceNotFoundException("Turma " + idTurma + " não encontrada no MongoDB"));

        TurmaMapper.updateDocument(request, document);
        noSqlRepository.save(document);

        return TurmaMapper.toResponse(document);
    }

    @Transactional
    public void delete(Integer idTurma) {
        relationalRepository.findById(idTurma)
                .orElseThrow(() -> new ResourceNotFoundException("Turma " + idTurma + " não encontrada"));
        relationalRepository.deleteById(idTurma);

        noSqlRepository.findById(idTurma)
                .orElseThrow(() -> new ResourceNotFoundException("Turma " + idTurma + " não encontrada no MongoDB"));
        noSqlRepository.deleteById(idTurma);
    }
}