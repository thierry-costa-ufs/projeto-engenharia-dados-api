package com.ufs.engdados.domain.turma.service;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.mapper.TurmaMapper;
import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import com.ufs.engdados.domain.turma.repository.nosql.TurmaNoSqlRepository;
import com.ufs.engdados.domain.turma.repository.relational.TurmaRelationalRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRelationalRepository relRepository;
    private final TurmaNoSqlRepository noSqlRepository;
    private final TurmaMapper mapper;
    private final EntityManager entityManager;

    public TurmaService(TurmaRelationalRepository relRepository,
                        TurmaNoSqlRepository noSqlRepository,
                        TurmaMapper mapper,
                        EntityManager entityManager) {
        this.relRepository = relRepository;
        this.noSqlRepository = noSqlRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Transactional
    public void salvarTurma(TurmaDTO dto) {
        Disciplina disciplina = null;
        if (dto.codDisc() != null) {
            disciplina = entityManager.getReference(Disciplina.class, dto.codDisc());
        }

        Turma entity = mapper.toEntity(dto, disciplina);
        entity = relRepository.save(entity);

        // força o PostgreSQL a gravar e gerar o ID imediatamente
        entityManager.flush();

        TurmaDocument document = mapper.toDocument(dto);
        // entity.getIdTurma() não está vazio
        document.setIdTurma(entity.getIdTurma());

        noSqlRepository.save(document);
    }

    public List<TurmaDTO> listarTodasRelacional() {
        return relRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<TurmaDTO> listarTodasNoSql() {
        return noSqlRepository.findAll().stream()
                .map(doc -> new TurmaDTO(
                        doc.getIdTurma(),
                        doc.getCodDisc(),
                        doc.getNumero(),
                        doc.getAno() != null ? doc.getAno().intValue() : null,
                        doc.getSemestre() != null ? doc.getSemestre().intValue() : null
                ))
                .toList();
    }

    @Transactional
    public void atualizarTurma(Integer idTurma, TurmaDTO dto) {
        Turma entity = relRepository.findById(idTurma)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Turma não encontrada"));

        entity.setNumero(dto.numero());
        entity.setAno(dto.ano() != null ? dto.ano().shortValue() : null);
        entity.setSemestre(dto.semestre() != null ? dto.semestre().shortValue() : null);

        if (dto.codDisc() != null) {
            entity.setDisciplina(entityManager.getReference(com.ufs.engdados.domain.disciplina.model.relational.Disciplina.class, dto.codDisc()));
        }

        relRepository.save(entity);

        // atualização no MongoDB
        noSqlRepository.findById(idTurma).ifPresent(doc -> {
            doc.setCodDisc(dto.codDisc());
            doc.setNumero(dto.numero());
            doc.setAno(dto.ano() != null ? dto.ano().shortValue() : null);
            doc.setSemestre(dto.semestre() != null ? dto.semestre().shortValue() : null);
            noSqlRepository.save(doc);
        });
    }

    @Transactional
    public void deletarTurma(Integer idTurma) {
        // apaga no banco relacional (PostgreSQL)
        relRepository.deleteById(idTurma);

        // busca o documento exato no banco NoSQL (MongoDB) e força a exclusão
        noSqlRepository.findById(idTurma).ifPresent(documento -> {
            noSqlRepository.delete(documento);
        });
    }
}