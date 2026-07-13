package com.ufs.engdados.domain.professor.mapper;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.enums.TipoFormacao;
import com.ufs.engdados.domain.professor.enums.TipoJornada;
import com.ufs.engdados.domain.professor.model.nosql.ProfessorDocument;
import com.ufs.engdados.domain.professor.model.relational.Professor;

public class ProfessorMapper {

    public static Professor toEntity(ProfessorDTO.Request dto) {
        if (dto == null) return null;
        Professor prof = new Professor();
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());

        if (dto.formacao() != null) {
            prof.setFormacao(TipoFormacao.valueOf(dto.formacao()));
        }
        if (dto.jornada() != null) {
            prof.setJornada(TipoJornada.deString(dto.jornada()));
        }

        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setSalario(dto.salario());

        return prof;
    }

    public static ProfessorDocument toDocument(ProfessorDTO.Request dto) {
        if (dto == null) return null;
        ProfessorDocument prof = new ProfessorDocument();
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());

        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());

        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setSalario(dto.salario());

        return prof;
    }

    public static ProfessorDTO.Response toResponse(Professor p) {
        if (p == null) return null;
        return new ProfessorDTO.Response(
                null,
                p.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao() != null ? p.getFormacao().name() : null,
                p.getDataAdmissao(),
                p.getJornada() != null ? p.getJornada().getCodigoDb().toUpperCase() : null,
                p.getSalario()
        );
    }

    public static ProfessorDTO.Response toResponse(ProfessorDocument p) {
        if (p == null) return null;
        return new ProfessorDTO.Response(
                p.getId(),
                p.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao(),
                p.getDataAdmissao(),
                p.getJornada(),
                p.getSalario()
        );
    }

    public static void updateEntity(ProfessorDTO.Request dto, Professor prof) {
        if (dto == null || prof == null) return;
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());

        if (dto.formacao() != null) {
            prof.setFormacao(TipoFormacao.valueOf(dto.formacao()));
        }
        if (dto.jornada() != null) {
            prof.setJornada(TipoJornada.deString(dto.jornada()));
        }

        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setSalario(dto.salario());
    }

    public static void updateDocument(ProfessorDTO.Request dto, ProfessorDocument prof){
        if (dto == null || prof == null) return;
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());
        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setSalario(dto.salario());
    }
}