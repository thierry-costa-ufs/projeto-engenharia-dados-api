package com.ufs.engdados.domain.cursa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface CursaDTO {

        record Request(
                @NotBlank(message = "A matrícula do estudante é obrigatória")
                @Size(min = 1, max = 7, message = "A matrícula deve ter no máximo 7 caracteres")
                @JsonProperty("mat_estudante")
                String matEstudante,

                @NotNull(message = "O ID da turma é obrigatório")
                @JsonProperty("id_turma")
                Integer idTurma,

                @JsonProperty("nota")
                Float nota
        ) {}

        record Response(
                String mongoId,
                @JsonProperty("id") // ID virtual gerado pelo Mapper para o React
                String id,
                @JsonProperty("matEstudante")
                String matEstudante,
                @JsonProperty("idTurma")
                Integer idTurma,
                @JsonProperty("nota")
                Float nota
        ) {}
}