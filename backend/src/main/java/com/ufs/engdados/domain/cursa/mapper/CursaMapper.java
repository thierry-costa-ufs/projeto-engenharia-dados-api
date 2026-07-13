package com.ufs.engdados.domain.cursa.mapper;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.model.nosql.CursaDocument;
import com.ufs.engdados.domain.cursa.model.relational.Cursa;
import com.ufs.engdados.domain.cursa.model.relational.CursaId;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.turma.model.relational.Turma;

public class CursaMapper {

    public static Cursa toEntity(CursaDTO.Request request, Estudante estudante, Turma turma) {
        Cursa entity = new Cursa();
        entity.setEstudante(estudante);
        entity.setTurma(turma);
        entity.setNota(request.nota());

        // garante o preenchimento da chave composta:
        CursaId cursaId = new CursaId(request.matEstudante(), request.idTurma());
        entity.setId(cursaId);

        return entity;
    }

    public static CursaDocument toDocument(CursaDTO.Request request) {
        CursaDocument doc = new CursaDocument();
        doc.setId(request.matEstudante() + "_" + request.idTurma());
        doc.setMatEstudante(request.matEstudante());
        doc.setIdTurma(request.idTurma());
        doc.setNota(request.nota());
        return doc;
    }

    public static CursaDTO.Response toResponse(Cursa entity) {
        if (entity == null) return null;

        String idVirtual = entity.getId().getMatEstudante() + "/" + entity.getId().getIdTurma();

        return new CursaDTO.Response(
                null,
                idVirtual,
                entity.getId().getMatEstudante(),
                entity.getId().getIdTurma(),
                entity.getNota()
        );
    }

    public static CursaDTO.Response toResponse(CursaDocument doc) {
        if (doc == null) return null;

        String idVirtual = doc.getMatEstudante() + "/" + doc.getIdTurma();

        return new CursaDTO.Response(
                doc.getId(),
                idVirtual,
                doc.getMatEstudante(),
                doc.getIdTurma(),
                doc.getNota()
        );
    }

    public static void updateEntity(CursaDTO.Request request, Cursa entity) {
        if (request == null || entity == null) return;
        entity.setNota(request.nota());
    }

    public static void updateDocument(CursaDTO.Request request, CursaDocument doc) {
        if (request == null || doc == null) return;
        doc.setNota(request.nota());
    }
}