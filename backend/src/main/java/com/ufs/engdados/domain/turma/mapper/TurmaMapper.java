package com.ufs.engdados.domain.turma.mapper;

import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import com.ufs.engdados.domain.turma.dto.TurmaDTO;
import com.ufs.engdados.domain.turma.model.nosql.TurmaDocument;
import com.ufs.engdados.domain.turma.model.relational.Turma;

public class TurmaMapper {

    public static Turma toEntity(TurmaDTO.Request request, Disciplina disciplina) {
        Turma entity = new Turma();
        entity.setDisciplina(disciplina);
        entity.setNumero(request.numero());
        entity.setAno(request.ano() != null ? request.ano().shortValue() : null);
        entity.setSemestre(request.semestre() != null ? request.semestre().shortValue() : null);
        return entity;
    }

    public static TurmaDocument toDocument(TurmaDTO.Request request, Integer idTurma) {
        TurmaDocument doc = new TurmaDocument();
        doc.setIdTurma(idTurma);
        doc.setCodDisc(request.codDisc());
        doc.setNumero(request.numero());
        doc.setAno(request.ano() != null ? request.ano().shortValue() : null);
        doc.setSemestre(request.semestre() != null ? request.semestre().shortValue() : null);
        return doc;
    }

    public static TurmaDTO.Response toResponse(Turma entity) {
        if (entity == null) return null;

        String codigo = entity.getDisciplina() != null ? entity.getDisciplina().getCodDisc() : null;

        return new TurmaDTO.Response(
                null,                  // mongoId
                entity.getIdTurma(),   // id
                entity.getIdTurma(),   // idTurma
                entity.getNumero(),    // codigoTurma
                entity.getNumero(),    // numero
                codigo,                // codigoDisciplina
                codigo,                // codDisciplina
                codigo,                // cod_disc (O que o formulário de edição precisa!)
                entity.getAno() != null ? entity.getAno().intValue() : null,
                entity.getSemestre() != null ? entity.getSemestre().intValue() : null,
                null, null, null
        );
    }

    public static TurmaDTO.Response toResponse(TurmaDocument doc) {
        if (doc == null) return null;

        String codigo = doc.getCodDisc();

        return new TurmaDTO.Response(
                null,                  // mongoId
                doc.getIdTurma(),      // id
                doc.getIdTurma(),      // idTurma
                doc.getNumero(),       // codigoTurma
                doc.getNumero(),       // numero
                codigo,                // codigoDisciplina
                codigo,                // codDisciplina
                codigo,                // cod_disc
                doc.getAno() != null ? doc.getAno().intValue() : null,
                doc.getSemestre() != null ? doc.getSemestre().intValue() : null,
                null, null, null
        );
    }

    public static void updateEntity(TurmaDTO.Request request, Turma entity, Disciplina disciplina) {
        if (request == null || entity == null) return;
        entity.setNumero(request.numero());
        entity.setAno(request.ano() != null ? request.ano().shortValue() : null);
        entity.setSemestre(request.semestre() != null ? request.semestre().shortValue() : null);
        if (disciplina != null) {
            entity.setDisciplina(disciplina);
        }
    }

    public static void updateDocument(TurmaDTO.Request request, TurmaDocument doc) {
        if (request == null || doc == null) return;
        doc.setCodDisc(request.codDisc());
        doc.setNumero(request.numero());
        doc.setAno(request.ano() != null ? request.ano().shortValue() : null);
        doc.setSemestre(request.semestre() != null ? request.semestre().shortValue() : null);
    }
}