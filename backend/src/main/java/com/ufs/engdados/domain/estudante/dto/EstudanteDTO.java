package com.ufs.engdados.domain.estudante.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface EstudanteDTO{

    record Request(
            @NotBlank(message = "A matrícula do estudante é obrigatória")
            @Size(max = 7, message = "A matrícula deve ter no máximo 7 caracteres")
            String matEstudante,

            @NotNull(message = "O cpf é obrigatório")
            @Size(max = 13, message = "O cpf deve ter no máximo 13 caracteres")
            Long cpf,

            @Digits(integer = 2, fraction = 2, message = "A média deve ter no máximo 2 dígitos inteiros e 2 casas decimais")
            @NotNull(message = "A média é obrigatória")
            Double mc,

            @NotNull(message = "O ano do ingresso é obrigatório")
            Integer anoIngresso

    ){}

    record Response(
            String mongoId,
            String matEstudante,
            Long cpf,
            Double mc,
            Integer anoIngresso
    ){}

}