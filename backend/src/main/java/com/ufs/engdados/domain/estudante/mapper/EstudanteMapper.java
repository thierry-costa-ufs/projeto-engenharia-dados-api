package com.ufs.engdados.domain.estudante.mapper;

import com.ufs.engdados.domain.estudante.dto.EstudanteDTO;
import com.ufs.engdados.domain.estudante.model.nosql.EstudanteDocument;
import com.ufs.engdados.domain.estudante.model.relational.Estudante;

public class EstudanteMapper {

    public static Estudante toEntity(EstudanteDTO.Request request){
        Estudante estudante = new Estudante();
        estudante.setMatEstudante(request.matEstudante());
        estudante.setCpf(request.cpf());
        estudante.setMc(request.mc());
        estudante.setAnoIngresso(request.anoIngresso());
        return estudante;
    }

    public static EstudanteDocument toDocument(EstudanteDTO.Request request){
        EstudanteDocument estudanteDocument = new EstudanteDocument();
        estudanteDocument.setMatEstudante(request.matEstudante());
        estudanteDocument.setCpf(request.cpf());
        estudanteDocument.setMc(request.mc());
        estudanteDocument.setAnoIngresso(request.anoIngresso());
        return estudanteDocument;
    }

    public static EstudanteDTO.Response toResponse(Estudante estudante){
        if(estudante == null) return null;
        return new EstudanteDTO.Response(
                null,
                estudante.getMatEstudante(),
                estudante.getCpf(),
                estudante.getMc(),
                estudante.getAnoIngresso()
        );
    }

    public static EstudanteDTO.Response toResponse(EstudanteDocument estudante) {
        return new EstudanteDTO.Response(
                estudante.getId(),
                estudante.getMatEstudante(),
                estudante.getCpf(),
                estudante.getMc(),
                estudante.getAnoIngresso()
        );
    }

    public static void updateEntity(EstudanteDTO.Request request, Estudante estudante){
        if(request == null || estudante == null) return;
        estudante.setMatEstudante(request.matEstudante());
        estudante.setCpf(request.cpf());
        estudante.setMc(request.mc());
        estudante.setAnoIngresso(request.anoIngresso());
    }

    public static void updateDocument(EstudanteDTO.Request request, EstudanteDocument estudante){
        if(request == null || estudante == null) return;
        estudante.setMatEstudante(request.matEstudante());
        estudante.setCpf(request.cpf());
        estudante.setMc(request.mc());
        estudante.setAnoIngresso(request.anoIngresso());
    }
}