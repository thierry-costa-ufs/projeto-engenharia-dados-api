package com.ufs.engdados.domain.vinculo.dto;

import java.time.LocalDate;

public class VinculoDTO {

    public record Request(
            String matEstudante,
            Long codCurso,
            LocalDate dataEntrada,
            String status,
            LocalDate dataSaida
    ) {}

    public record Response(
            Long idVinculo,
            String mongoId,
            String matEstudante,
            Long codCurso,
            LocalDate dataEntrada,
            String status,
            LocalDate dataSaida,
            String statusExecucao
    ) {}
}