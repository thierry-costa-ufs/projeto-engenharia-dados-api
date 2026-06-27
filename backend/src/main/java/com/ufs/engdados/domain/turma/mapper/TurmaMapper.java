package com.ufs.engdados.domain.turma.mapper;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapper {

    public Turma toEntity(TurmaDTO dto, Disciplina disciplina) {
        Turma entity = new Turma();
        entity.setIdTurma(dto.idTurma());
        entity.setDisciplina(disciplina);
        entity.setNumero(dto.numero());
        entity.setAno(dto.ano() != null ? dto.ano().shortValue() : null);
        entity.setSemestre(dto.semestre() != null ? dto.semestre().shortValue() : null);
        return entity;
    }

    public TurmaDocument toDocument(TurmaDTO dto) {
        TurmaDocument doc = new TurmaDocument();
        doc.setIdTurma(dto.idTurma());
        doc.setCodDisc(dto.codDisc());
        doc.setNumero(dto.numero());
        doc.setAno(dto.ano() != null ? dto.ano().shortValue() : null);
        doc.setSemestre(dto.semestre() != null ? dto.semestre().shortValue() : null);
        return doc;
    }

    public TurmaDTO toDto(Turma entity) {
        String codDisc = (entity.getDisciplina() != null) ? entity.getDisciplina().getCodDisc() : null;

        return new TurmaDTO(
                entity.getIdTurma(),
                codDisc,
                entity.getNumero(),
                entity.getAno() != null ? entity.getAno().intValue() : null,
                entity.getSemestre() != null ? entity.getSemestre().intValue() : null
        );
    }
}