package com.ufs.engdados.domain.professor.mapper;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.model.nosql.ProfessorDocument;
import com.ufs.engdados.domain.professor.model.relational.Professor;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ProfessorMapper {

    public static Professor toEntity(ProfessorDTO.Request dto) {
        if (dto == null) return null;
        Professor prof = new Professor();
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());
        prof.setDataAdmissao(dto.dataAdmissao());

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

        return prof;
    }

    public static ProfessorDTO.Response toResponse(Professor p) {
        if (p == null) return null;
        return new ProfessorDTO.Response(
                p.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao(),
                p.getDataAdmissao(),
                p.getJornada(),
                p.getSalario() != null ? p.getSalario() : null
        );
    }

    public static ProfessorDTO.Response toResponse(ProfessorDocument p) {
        if (p == null) return null;
        return new ProfessorDTO.Response(
                p.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao(),
                p.getDataAdmissao(),
                p.getJornada(),
                p.getSalario() != null ? p.getSalario() : null
        );
    }

    public static void updateEntity(ProfessorDTO.Request dto, Professor prof) {
        if (dto == null || prof == null) return;
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setJornada(dto.jornada());
        prof.setSalario(dto.salario());
    }

    public static void updateDocument(ProfessorDTO.Request dto, ProfessorDocument prof){
        if (dto == null || prof == null) return;
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setDataAdmissao(dto.dataAdmissao());
        prof.setJornada(dto.jornada());
        prof.setSalario(dto.salario());
    }

}