package com.ufs.engdados.domain.disciplina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface DisciplinaDTO {

        record Request(
                @NotBlank(message = "O código da disciplina é obrigatório")
                @Size(max = 8, message = "O código da disciplina deve ter no máximo 8 caracteres")
                @JsonProperty("cod_disc")
                String codDisc,

                @NotBlank(message = "O nome da disciplina é obrigatório")
                @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
                String nome,

                @NotNull(message = "Os créditos são obrigatórios")
                Integer creditos,

                @JsonProperty("pre_req")
                String preReq,

                @JsonProperty("depto_responsavel")
                String deptoResponsavel
        ) {}

        record Response(
                String mongoId,
                @JsonProperty("id")
                String id,
                @JsonProperty("codigoDisciplina")
                String codDisc,
                String nome,
                Integer creditos,
                @JsonProperty("preReq")
                String preReq,
                @JsonProperty("codDepartamento")
                String deptoResponsavel
        ) {}
}