package com.ufs.engdados.domain.departamento.mapper;

import com.ufs.engdados.domain.departamento.dto.DepartamentoDTO;
import com.ufs.engdados.domain.departamento.model.nosql.DepartamentoDocument;
import com.ufs.engdados.domain.departamento.model.relational.Departamento;

public class DepartamentoMapper{

    public static Departamento toEntity(DepartamentoDTO.Request request){
        Departamento departamento = new Departamento();
        departamento.setCodDepto(request.codDepto());
        departamento.setNome(request.nome());
        departamento.setChefe(request.chefe());
        departamento.setOrcamento(request.orcamento());
        departamento.setComissal(request.comissal());
        return departamento;
    }

    public static DepartamentoDocument toDocument(DepartamentoDTO.Request request) {
        DepartamentoDocument departamento = new DepartamentoDocument();
        departamento.setCodDepto(request.codDepto());
        departamento.setNome(request.nome());
        departamento.setChefe(request.chefe());
        departamento.setOrcamento(request.orcamento());
        departamento.setComissal(request.comissal());
        return departamento;
    }

    public static DepartamentoDTO.Response toResponse(Departamento departamento){
        return new DepartamentoDTO.Response(
                null,
                departamento.getCodDepto(),
                departamento.getNome(),
                departamento.getChefe(),
                departamento.getOrcamento(),
                departamento.getComissal()
        );
    }

    public static DepartamentoDTO.Response toResponse(DepartamentoDocument departamento) {
        return new DepartamentoDTO.Response(
                departamento.getId(),
                departamento.getCodDepto(),
                departamento.getNome(),
                departamento.getChefe(),
                departamento.getOrcamento(),
                departamento.getComissal()
        );
    }
}