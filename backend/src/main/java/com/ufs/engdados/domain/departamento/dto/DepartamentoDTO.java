package com.ufs.engdados.domain.departamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public interface DepartamentoDTO{

    record Request(
            @NotBlank(message = "O código do departamento é obrigatório")
            @Size(max = 5, message = "O código do departamento deve ter no máximo 5 carcteres")
            String codDepto,

            @NotBlank(message = "O nome  é obrigatório")
            @Size(max = 50, message = "O nome deve ter no máximo 50 carcteres")
            String nome,

            @Size(max = 7, message = "O chefe deve ter no máximo 7 caracteres.")
            String chefe,

            BigDecimal orcamento,

            BigDecimal comissal
    ){}

    record Response(
            String mongoId,
            String codDepto,
            String nome,
            String chefe,
            BigDecimal orcamento,
            BigDecimal comissal
    ){}

}