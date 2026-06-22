package com.ufs.engdados.domain.estudante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface EstudanteDTO{

    record Request(
            @NotBlank(message = "A matrícula do estudante é obrigatória")
            String matEstudante,

            @NotNull(message = "O cpf é obrigatório")
            Long cpf,

            Integer mc,

            @NotNull(message = "O ano do ingresso é obrigatório")
            Integer anoIngresso

    ){}

    record Response(
            String mongoId,
            String matEstudante,
            Long cpf,
            Integer mc,
            Integer anoIngresso
    ){}

}