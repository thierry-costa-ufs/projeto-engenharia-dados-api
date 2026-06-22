package com.ufs.engdados.domain.professor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public interface ProfessorDTO {

    record Request(
            @NotNull(message = "O CPF do usuário é obrigatório.")
            Long cpf,

            @NotBlank(message = "A matrícula é obrigatória.")
            @Size(max = 7, message = "A matrícula deve ter no máximo 7 caracteres.")
            String matricula,

            @NotBlank(message = "O departamento é obrigatório.")
            @Size(max = 5, message = "O departamento deve ter no máximo 5 caracteres.")
            String departamento,

            @NotBlank(message = "A formação é obrigatória.")
            String formacao,

            @NotBlank(message = "A jornada de trabalho é obrigatória.")
            String jornada,

            @NotNull(message = "O salário é obrigatório.")
            @Positive(message = "O salário deve ser maior que zero.")
            Double salario,

            @NotNull(message = "A data de admissão é obrigatória.")
            LocalDate dataAdmissao
    ) {}

    record Response(
            Long cpf,
            String matricula,
            String departamento,
            String formacao,
            String jornada,
            Double salario,
            java.time.LocalDate dataAdmissao,
            String statusSincronizacao,
            String nome
    ) {}
}