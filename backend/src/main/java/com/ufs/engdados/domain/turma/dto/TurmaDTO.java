package com.ufs.engdados.domain.turma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface TurmaDTO {

    record Request(
            @NotBlank(message = "O código da disciplina é obrigatório")
            @Size(max = 8, message = "O código da disciplina deve ter no máximo 8 caracteres")
            @JsonProperty("cod_disc")
            String codDisc,

            @NotNull(message = "O número da turma é obrigatório")
            Integer numero,

            @NotNull(message = "O ano é obrigatório")
            Integer ano,

            @NotNull(message = "O semestre é obrigatório")
            Integer semestre
    ) {}

    record Response(
            String mongoId,

            @JsonProperty("id") Integer id,
            Integer idTurma,

            @JsonProperty("codigoTurma") Integer codigoTurma,
            Integer numero,

            @JsonProperty("codigoDisciplina") String codigoDisciplina,
            @JsonProperty("codDisciplina") String codDisciplina,
            @JsonProperty("cod_disc") String codDisc,

            Integer ano,
            Integer semestre,
            String matriculaProfessor,
            String horario,
            String sala
    ) {}
}