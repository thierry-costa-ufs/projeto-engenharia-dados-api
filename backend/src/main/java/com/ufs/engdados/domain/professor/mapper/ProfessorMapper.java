package com.ufs.engdados.domain.professor.mapper;

import com.ufs.engdados.domain.professor.dto.ProfessorDTO;
import com.ufs.engdados.domain.professor.model.nosql.ProfessorDocument;
import com.ufs.engdados.domain.professor.model.relational.Professor;
import com.ufs.engdados.domain.usuario.model.nosql.UsuarioDocument;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorDTO.Request dto) {
        if (dto == null) return null;
        Professor prof = new Professor();
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());

        if (dto.salario() != null) {
            prof.setSalario(BigDecimal.valueOf(dto.salario()));
        }

        prof.setDataAdmissao(dto.dataAdmissao());
        return prof;
    }

    public ProfessorDocument toDocument(ProfessorDTO.Request dto) {
        if (dto == null) return null;
        ProfessorDocument prof = new ProfessorDocument();
        prof.setCpf(dto.cpf());
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());

        if (dto.salario() != null) {
            prof.setSalario(BigDecimal.valueOf(dto.salario()));
        }

        prof.setDataAdmissao(dto.dataAdmissao());
        return prof;
    }


    public void updateEntityFromDto(ProfessorDTO.Request dto, Professor prof) {
        if (dto == null || prof == null) return;
        prof.setMatricula(dto.matricula());
        prof.setDepartamento(dto.departamento());
        prof.setFormacao(dto.formacao());
        prof.setJornada(dto.jornada());

        if (dto.salario() != null) {
            prof.setSalario(BigDecimal.valueOf(dto.salario()));
        }

        prof.setDataAdmissao(dto.dataAdmissao());
    }

    public ProfessorDTO.Response toResponse(Professor p, String status) {
        if (p == null) return null;

        String nomeUsuario = (p.getUsuario() != null) ? p.getUsuario().getNome() : null;

        return new ProfessorDTO.Response(
                p.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao(),
                p.getJornada(),
                p.getSalario() != null ? p.getSalario().doubleValue() : null,
                p.getDataAdmissao(),
                status,
                nomeUsuario
        );
    }

    public ProfessorDTO.Response toResponse(UsuarioDocument doc) {
        if (doc == null || doc.getPerfilProfessor() == null) return null;
        var p = doc.getPerfilProfessor();
        return new ProfessorDTO.Response(
                doc.getCpf(),
                p.getMatricula(),
                p.getDepartamento(),
                p.getFormacao(),
                p.getJornada(),
                p.getSalario(),
                p.getDataAdmissao(),
                "INTEGRADO_NOSQL",
                doc.getNome()
        );
    }
}