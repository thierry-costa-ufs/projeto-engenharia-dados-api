package com.ufs.engdados.domain.disciplina.service;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.mapper.DisciplinaMapper;
import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.disciplina.repository.nosql.DisciplinaNoSqlRepository;
import com.ufs.engdados.domain.disciplina.repository.relational.DisciplinaRelationalRepository;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {

    private final DisciplinaRelationalRepository relRepository;
    private final DisciplinaNoSqlRepository noSqlRepository;
    private final EntityManager entityManager;

    public DisciplinaService(DisciplinaRelationalRepository relRepository,
                             DisciplinaNoSqlRepository noSqlRepository,
                             EntityManager entityManager) {
        this.relRepository = relRepository;
        this.noSqlRepository = noSqlRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public DisciplinaDTO.Response create(DisciplinaDTO.Request request) {
        Departamento depto = null;
        if (request.deptoResponsavel() != null && !request.deptoResponsavel().isBlank()) {
            depto = entityManager.getReference(Departamento.class, request.deptoResponsavel());
        }

        Disciplina preReq = null;
        if (request.preReq() != null && !request.preReq().isBlank()) {
            preReq = relRepository.findById(request.preReq()).orElse(null);
        }

        Disciplina entity = DisciplinaMapper.toEntity(request, depto, preReq);
        DisciplinaDocument document = DisciplinaMapper.toDocument(request);

        entity = relRepository.save(entity);
        DisciplinaDocument salvoNoSql = noSqlRepository.save(document);

        return DisciplinaMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<DisciplinaDTO.Response> findAllRelational(Pageable pageable) {
        Page<Disciplina> disciplinas = relRepository.findAll(pageable);
        return disciplinas.map(DisciplinaMapper::toResponse);
    }

    public Page<DisciplinaDTO.Response> findAllNoSql(Pageable pageable) {
        Page<DisciplinaDocument> disciplinas = noSqlRepository.findAll(pageable);
        return disciplinas.map(DisciplinaMapper::toResponse);
    }

    @Transactional
    public DisciplinaDTO.Response update(String codDisc, DisciplinaDTO.Request request) {
        Disciplina entity = relRepository.findById(codDisc)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina " + codDisc + " não encontrada"));

        Departamento depto = null;
        if (request.deptoResponsavel() != null && !request.deptoResponsavel().isBlank()) {
            depto = entityManager.getReference(Departamento.class, request.deptoResponsavel());
        }

        Disciplina preReq = null;
        if (request.preReq() != null && !request.preReq().isBlank()) {
            preReq = relRepository.findById(request.preReq()).orElse(null);
        }

        DisciplinaMapper.updateEntity(request, entity, depto, preReq);
        relRepository.save(entity);

        DisciplinaDocument document = noSqlRepository.findById(codDisc)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina " + codDisc + " não encontrada no MongoDB"));

        DisciplinaMapper.updateDocument(request, document);
        noSqlRepository.save(document);

        return DisciplinaMapper.toResponse(document);
    }

    @Transactional
    public void delete(String codDisc) {
        relRepository.findById(codDisc)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina " + codDisc + " não encontrada"));
        relRepository.deleteById(codDisc);

        noSqlRepository.findById(codDisc)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina " + codDisc + " não encontrada no MongoDB"));
        noSqlRepository.deleteById(codDisc);
    }
}