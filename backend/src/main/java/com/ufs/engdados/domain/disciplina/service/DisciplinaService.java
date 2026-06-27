package com.ufs.engdados.domain.disciplina.service;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.mapper.DisciplinaMapper;
import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.disciplina.repository.nosql.DisciplinaNoSqlRepository;
import com.ufs.engdados.domain.disciplina.repository.relational.DisciplinaRelationalRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRelationalRepository relRepository;
    private final DisciplinaNoSqlRepository noSqlRepository;
    private final DisciplinaMapper mapper;
    private final EntityManager entityManager;

    public DisciplinaService(DisciplinaRelationalRepository relRepository,
                             DisciplinaNoSqlRepository noSqlRepository,
                             DisciplinaMapper mapper,
                             EntityManager entityManager) {
        this.relRepository = relRepository;
        this.noSqlRepository = noSqlRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Transactional
    public void salvarDisciplina(DisciplinaDTO dto) {
        Departamento depto = null;
        if (dto.deptoResponsavel() != null) {
            depto = entityManager.getReference(Departamento.class, dto.deptoResponsavel());
        }

        Disciplina preReq = null;
        if (dto.preReq() != null) {
            preReq = relRepository.findById(dto.preReq()).orElse(null);
        }

        Disciplina entity = mapper.toEntity(dto, depto, preReq);
        DisciplinaDocument document = mapper.toDocument(dto);

        relRepository.save(entity);
        noSqlRepository.save(document);
    }

    // busca apenas no PostgreSQL
    public List<DisciplinaDTO> listarTodasRelacional() {
        return relRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    // busca apenas no MongoDB e converte direto para DTO
    public List<DisciplinaDTO> listarTodasNoSql() {
        return noSqlRepository.findAll().stream()
                .map(doc -> new DisciplinaDTO(
                        doc.getCodDisc(),
                        doc.getNome(),
                        doc.getCreditos(),
                        doc.getPreReq(),
                        doc.getDeptoResponsavel()
                ))
                .toList();
    }

    @Transactional
    public void atualizarDisciplina(String codDisc, DisciplinaDTO dto) {
        Disciplina entity = relRepository.findById(codDisc)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Disciplina não encontrada"));

        entity.setNome(dto.nome());
        entity.setCreditos(dto.creditos());

        // só atualiza o departamento se ele não for nulo E não for um texto vazio ("")
        if (dto.deptoResponsavel() != null && !dto.deptoResponsavel().isBlank()) {
            entity.setDeptoResponsavel(entityManager.getReference(com.ufs.engdados.domain.departamento.model.relational.Departamento.class, dto.deptoResponsavel()));
        } else {
            entity.setDeptoResponsavel(null);
        }

        // só atualiza o pré-requisito se não for vazio ("")
        if (dto.preReq() != null && !dto.preReq().isBlank()) {
            entity.setPreReq(relRepository.findById(dto.preReq()).orElse(null));
        } else {
            entity.setPreReq(null);
        }

        relRepository.save(entity);

        // atualização no MongoDB
        noSqlRepository.findById(codDisc).ifPresent(doc -> {
            doc.setNome(dto.nome());
            doc.setCreditos(dto.creditos());
            doc.setDeptoResponsavel(dto.deptoResponsavel());
            doc.setPreReq(dto.preReq());
            noSqlRepository.save(doc);
        });
    }

    @Transactional
    public void deletarDisciplina(String codDisc) {
        relRepository.deleteById(codDisc);
        noSqlRepository.deleteById(codDisc);
    }
}