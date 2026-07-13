package com.ufs.engdados.domain.vinculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public interface VinculoDTO {

    record Request(
            Long idVinculo,

            @Size(max = 7, message = "A matrícula deve ter no máximo 7 caracteres")
            String matEstudante,

            Integer codCurso,

            LocalDate dataEntrada,

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