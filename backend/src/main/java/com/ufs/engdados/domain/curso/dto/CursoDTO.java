package com.ufs.engdados.domain.curso.dto;

import com.ufs.engdados.domain.curso.enums.TipoGrau;
import com.ufs.engdados.domain.curso.enums.TipoNivel;
import com.ufs.engdados.domain.curso.enums.TipoTurno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface CursoDTO {

    record Request(
            @NotBlank(message = "O nome do curso é obrigatório.")
            @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
            String nome,

            @NotBlank(message = "O grau acadêmico é obrigatório.")
            String grau,

            @NotBlank(message = "O turno do curso é obrigatório.")
            String turno,

            @NotBlank(message = "O campus é obrigatório.")
            @Size(max = 100, message = "O campus não pode exceder 100 caracteres.")
            String campus,

            @NotBlank(message = "O nível do curso é obrigatório.")
            String nivel
    ) {}

    record Response(
            Integer idCurso,
            String nome,
            TipoGrau grau,
            TipoTurno turno,
            String campus,
            TipoNivel nivel,
            String status
    ) {}
}