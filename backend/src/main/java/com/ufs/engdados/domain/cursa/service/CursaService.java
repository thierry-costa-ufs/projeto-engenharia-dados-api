package com.ufs.engdados.domain.cursa.service;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.mapper.CursaMapper;
import com.ufs.engdados.domain.cursa.model.nosql.CursaDocument;
import com.ufs.engdados.domain.cursa.model.relational.Cursa;
import com.ufs.engdados.domain.cursa.repository.nosql.CursaNoSqlRepository;
import com.ufs.engdados.domain.cursa.repository.relational.CursaRelationalRepository;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursaService {

    private final CursaRelationalRepository relRepository;
    private final CursaNoSqlRepository noSqlRepository;
    private final CursaMapper mapper;
    private final EntityManager entityManager;

    public CursaService(CursaRelationalRepository relRepository,
                        CursaNoSqlRepository noSqlRepository,
                        CursaMapper mapper,
                        EntityManager entityManager) {
        this.relRepository = relRepository;
        this.noSqlRepository = noSqlRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Transactional
    public void salvarCursa(CursaDTO dto) {
        Estudante estudante = entityManager.getReference(Estudante.class, dto.matEstudante());
        Turma turma = entityManager.getReference(Turma.class, dto.idTurma());

        Cursa entity = mapper.toEntity(dto, estudante, turma);

        // salva no relacional
        relRepository.save(entity);

        // força a validação imediata do PostgreSQL (barrando alunos que não existem)
        entityManager.flush();

        CursaDocument document = mapper.toDocument(dto);

        // obriga o MongoDB a usar o mesmo padrão de ID da edição/remoção
        document.setId(dto.matEstudante() + "_" + dto.idTurma());

        noSqlRepository.save(document);
    }

    // busca apenas no PostgreSQL
    public List<CursaDTO> listarTodasRelacional() {
        return relRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    // busca apenas no MongoDB
    public List<CursaDTO> listarTodasNoSql() {
        return noSqlRepository.findAll().stream()
                .map(doc -> new CursaDTO(
                        doc.getMatEstudante(),
                        doc.getIdTurma(),
                        doc.getNota()
                ))
                .toList();
    }

    @Transactional
    public void atualizarCursa(String matEstudante, Integer idTurma, CursaDTO dto) {
        Cursa entity = relRepository.findAll().stream()
                .filter(c -> c.getId().getMatEstudante().equals(matEstudante) && c.getId().getIdTurma().equals(idTurma))
                .findFirst()
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Registo de matrícula não encontrado"));

        entity.setNota(dto.nota());
        relRepository.save(entity);

        // Atualização no MongoDB
        String noSqlId = matEstudante + "_" + idTurma;
        noSqlRepository.findById(noSqlId).ifPresent(doc -> {
            doc.setNota(dto.nota());
            noSqlRepository.save(doc);
        });
    }

    @Transactional
    public void deletarCursa(String matEstudante, Integer idTurma) {
        Cursa entity = relRepository.findAll().stream()
                .filter(c -> c.getId().getMatEstudante().equals(matEstudante) && c.getId().getIdTurma().equals(idTurma))
                .findFirst()
                .orElse(null);

        if (entity != null) {
            relRepository.delete(entity);
        }

        // remoção segura no MongoDB
        String noSqlId = matEstudante + "_" + idTurma;
        noSqlRepository.findById(noSqlId).ifPresent(doc -> {
            noSqlRepository.delete(doc);
        });
    }
}