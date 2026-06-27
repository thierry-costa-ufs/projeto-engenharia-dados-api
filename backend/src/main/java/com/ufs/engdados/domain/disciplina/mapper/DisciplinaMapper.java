package com.ufs.engdados.domain.disciplina.mapper;

import com.ufs.engdados.domain.departamento.model.relational.Departamento;
import com.ufs.engdados.domain.disciplina.dto.DisciplinaDTO;
import com.ufs.engdados.domain.disciplina.model.nosql.DisciplinaDocument;
import com.ufs.engdados.domain.disciplina.model.relational.Disciplina;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaMapper {

    // converte DTO para a entidade relacional (PostgreSQL)
    public Disciplina toEntity(DisciplinaDTO dto, Departamento depto, Disciplina preReq) {
        Disciplina entity = new Disciplina();
        entity.setCodDisc(dto.codDisc());
        entity.setNome(dto.nome());
        entity.setCreditos(dto.creditos());
        entity.setDeptoResponsavel(depto);
        entity.setPreReq(preReq);
        return entity;
    }

    // converte DTO para o documento NoSQL (MongoDB)
    public DisciplinaDocument toDocument(DisciplinaDTO dto) {
        DisciplinaDocument doc = new DisciplinaDocument();
        doc.setCodDisc(dto.codDisc());
        doc.setNome(dto.nome());
        doc.setCreditos(dto.creditos());
        doc.setDeptoResponsavel(dto.deptoResponsavel());
        doc.setPreReq(dto.preReq());
        return doc;
    }

    // converte a entidade do banco de volta para DTO para o Front-End
    public DisciplinaDTO toDto(Disciplina entity) {
        String preReqId = (entity.getPreReq() != null) ? entity.getPreReq().getCodDisc() : null;

        String deptoId = (entity.getDeptoResponsavel() != null) ? entity.getDeptoResponsavel().getCodDepto() : null;

        return new DisciplinaDTO(
                entity.getCodDisc(),
                entity.getNome(),
                entity.getCreditos(),
                preReqId,
                deptoId
        );
    }
}