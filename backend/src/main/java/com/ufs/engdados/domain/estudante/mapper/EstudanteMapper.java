package com.ufs.engdados.domain.estudante.mapper;

import com.ufs.engdados.domain.estudante.dto.EstudanteDTO;
import com.ufs.engdados.domain.estudante.model.nosql.EstudanteDocument;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;

public class EstudanteMapper {

    public static Estudante toRelational(EstudanteDTO.Request request){
        Estudante estudante = new Estudante();
        estudante.setMatEstudante(request.matEstudante());
        estudante.setCpf(request.cpf());
        estudante.setMc(request.mc());
        estudante.setAnoIngresso(request.anoIngresso());
        return estudante;
    }

    public static EstudanteDocument toNoSql(EstudanteDTO.Request request){
        EstudanteDocument estudanteDocument = new EstudanteDocument();
        estudanteDocument.setMatEstudante(request.matEstudante());
        estudanteDocument.setCpf(request.cpf());
        estudanteDocument.setMc(request.mc());
        estudanteDocument.setAnoIngresso(request.anoIngresso());
        return estudanteDocument;
    }

    public static EstudanteDTO.Response relationalToResponse(Estudante estudante){
        return new EstudanteDTO.Response(
                null,
                estudante.getMatEstudante(),
                estudante.getCpf(),
                estudante.getMc(),
                estudante.getAnoIngresso()
        );
    }

    public static EstudanteDTO.Response noSqlToResponse(EstudanteDocument estudante) {
        return new EstudanteDTO.Response(
                estudante.getId(),
                estudante.getMatEstudante(),
                estudante.getCpf(),
                estudante.getMc(),
                estudante.getAnoIngresso()
        );
    }
}