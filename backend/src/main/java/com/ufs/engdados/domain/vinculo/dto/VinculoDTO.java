package com.ufs.engdados.domain.vinculo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public interface VinculoDTO {

    record Request(
            Long idVinculo,

            @NotNull(message = "A matricula do estudante e obrigatoria.")
            @Size(max = 7, message = "A matricula deve ter no maximo 7 caracteres.")
            String matEstudante,

            @NotNull(message = "O codigo do curso e obrigatorio.")
            Integer codCurso,

            LocalDate dataEntrada,

            @NotNull(message = "O status e obrigatorio.")
            String status,

            LocalDate dataSaida
    ) {}

    record Response(
            String mongoId,
            Long idVinculo,
            String matEstudante,
            Integer codCurso,
            LocalDate dataEntrada,
            String status,
            LocalDate dataSaida
    ) {}
}
