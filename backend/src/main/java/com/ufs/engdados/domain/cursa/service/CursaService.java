package com.ufs.engdados.domain.cursa.service;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.mapper.CursaMapper;
import com.ufs.engdados.domain.cursa.model.nosql.CursaDocument;
import com.ufs.engdados.domain.cursa.model.relational.Cursa;
import com.ufs.engdados.domain.cursa.model.relational.CursaId;
import com.ufs.engdados.domain.cursa.repository.nosql.CursaNoSqlRepository;
import com.ufs.engdados.domain.cursa.repository.relational.CursaRelationalRepository;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import com.ufs.engdados.infrastructure.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursaService {

    private final CursaRelationalRepository relRepository;
    private final CursaNoSqlRepository noSqlRepository;
    private final EntityManager entityManager;

    public CursaService(CursaRelationalRepository relRepository,
                        CursaNoSqlRepository noSqlRepository,
                        EntityManager entityManager) {
        this.relRepository = relRepository;
        this.noSqlRepository = noSqlRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public CursaDTO.Response create(CursaDTO.Request request) {
        Estudante estudante = entityManager.getReference(Estudante.class, request.matEstudante());
        Turma turma = entityManager.getReference(Turma.class, request.idTurma());

        Cursa entity = CursaMapper.toEntity(request, estudante, turma);

        // salva no relacional
        relRepository.save(entity);

        // força a validação imediata do PostgreSQL (barrando alunos/turmas que não existem)
        entityManager.flush();

        CursaDocument document = CursaMapper.toDocument(request);
        CursaDocument salvoNoSql = noSqlRepository.save(document);

        return CursaMapper.toResponse(salvoNoSql);
    }

    @Transactional(readOnly = true)
    public Page<CursaDTO.Response> findAllRelational(Pageable pageable) {
        Page<Cursa> cursas = relRepository.findAll(pageable);
        return cursas.map(CursaMapper::toResponse);
    }

    public Page<CursaDTO.Response> findAllNoSql(Pageable pageable) {
        Page<CursaDocument> cursas = noSqlRepository.findAll(pageable);
        return cursas.map(CursaMapper::toResponse);
    }

    @Transactional
    public CursaDTO.Response update(String matEstudante, Integer idTurma, CursaDTO.Request request) {
        CursaId cursaId = new CursaId(matEstudante, idTurma);

        Cursa entity = relRepository.findById(cursaId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de matrícula não encontrado no PostgreSQL"));

        CursaMapper.updateEntity(request, entity);
        relRepository.save(entity);

        String noSqlId = matEstudante + "_" + idTurma;
        CursaDocument document = noSqlRepository.findById(noSqlId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de matrícula não encontrado no MongoDB"));

        CursaMapper.updateDocument(request, document);
        noSqlRepository.save(document);

        return CursaMapper.toResponse(document);
    }

    @Transactional
    public void delete(String matEstudante, Integer idTurma) {
        CursaId cursaId = new CursaId(matEstudante, idTurma);

        relRepository.findById(cursaId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de matrícula não encontrado no PostgreSQL"));
        relRepository.deleteById(cursaId);

        String noSqlId = matEstudante + "_" + idTurma;
        noSqlRepository.findById(noSqlId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de matrícula não encontrado no MongoDB"));
        noSqlRepository.deleteById(noSqlId);
    }
}