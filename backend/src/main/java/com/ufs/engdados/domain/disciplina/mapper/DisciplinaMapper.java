package com.ufs.engdados.domain.disciplina.mapper;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;

public class DisciplinaMapper {

    public static Disciplina toEntity(DisciplinaDTO.Request request, Departamento depto, Disciplina preReq) {
        Disciplina entity = new Disciplina();
        entity.setCodDisc(request.codDisc());
        entity.setNome(request.nome());
        entity.setCreditos(request.creditos());
        entity.setDeptoResponsavel(depto);
        entity.setPreReq(preReq);
        return entity;
    }

    public static DisciplinaDocument toDocument(DisciplinaDTO.Request request) {
        DisciplinaDocument doc = new DisciplinaDocument();
        doc.setCodDisc(request.codDisc());
        doc.setNome(request.nome());
        doc.setCreditos(request.creditos());
        doc.setDeptoResponsavel(request.deptoResponsavel());
        doc.setPreReq(request.preReq());
        return doc;
    }

    public static DisciplinaDTO.Response toResponse(Disciplina entity) {
        if (entity == null) return null;

        String preReqId = (entity.getPreReq() != null) ? entity.getPreReq().getCodDisc() : null;
        String deptoId = (entity.getDeptoResponsavel() != null) ? entity.getDeptoResponsavel().getCodDepto() : null;

        return new DisciplinaDTO.Response(
                null,
                entity.getCodDisc(),
                entity.getCodDisc(),
                entity.getNome(),
                entity.getCreditos(),
                preReqId,
                deptoId
        );
    }

    public static DisciplinaDTO.Response toResponse(DisciplinaDocument doc) {
        if (doc == null) return null;

        return new DisciplinaDTO.Response(
                doc.getCodDisc(), // no MongoDB, o código da disciplina é o ID
                doc.getCodDisc(),
                doc.getCodDisc(),
                doc.getNome(),
                doc.getCreditos(),
                doc.getPreReq(),
                doc.getDeptoResponsavel()
        );
    }

    public static void updateEntity(DisciplinaDTO.Request request, Disciplina entity, Departamento depto, Disciplina preReq) {
        if (request == null || entity == null) return;
        entity.setNome(request.nome());
        entity.setCreditos(request.creditos());
        entity.setDeptoResponsavel(depto);
        entity.setPreReq(preReq);
    }

    public static void updateDocument(DisciplinaDTO.Request request, DisciplinaDocument doc) {
        if (request == null || doc == null) return;
        doc.setNome(request.nome());
        doc.setCreditos(request.creditos());
        doc.setDeptoResponsavel(request.deptoResponsavel());
        doc.setPreReq(request.preReq());
    }
}