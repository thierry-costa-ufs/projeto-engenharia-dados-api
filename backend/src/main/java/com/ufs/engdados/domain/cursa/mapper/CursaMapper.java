package com.ufs.engdados.domain.cursa.mapper;

import com.ufs.engdados.domain.cursa.dto.CursaDTO;
import com.ufs.engdados.domain.cursa.model.nosql.CursaDocument;
import com.ufs.engdados.domain.cursa.model.relational.Cursa;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;
import com.ufs.engdados.domain.turma.model.relational.Turma;
import org.springframework.stereotype.Component;

@Component
public class CursaMapper {

    public Cursa toEntity(CursaDTO dto, Estudante estudante, Turma turma) {
        Cursa entity = new Cursa();
        entity.setEstudante(estudante);
        entity.setTurma(turma);
        entity.setNota(dto.nota());
        // garante o preenchimento da chave composta:
        entity.getId().setMatEstudante(dto.matEstudante());
        entity.getId().setIdTurma(dto.idTurma());
        return entity;
    }

    public CursaDocument toDocument(CursaDTO dto) {
        CursaDocument doc = new CursaDocument();
        doc.setId(dto.matEstudante() + "_" + dto.idTurma());
        doc.setMatEstudante(dto.matEstudante());
        doc.setIdTurma(dto.idTurma());
        doc.setNota(dto.nota());
        return doc;
    }

    // converte do banco para DTO
    public CursaDTO toDto(Cursa entity) {
        return new CursaDTO(
                entity.getId().getMatEstudante(),
                entity.getId().getIdTurma(),
                entity.getNota()
        );
    }
}