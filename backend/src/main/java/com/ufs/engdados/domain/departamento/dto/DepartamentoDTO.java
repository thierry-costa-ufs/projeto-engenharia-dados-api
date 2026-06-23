package com.ufs.engdados.domain.departamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public interface DepartamentoDTO{

    record Request(
            @NotBlank(message = "O código do departamento é obrigatório")
            @Size(max = 5, message = "O código do departamento deve ter no máximo 5 carcteres")
            String cod_depto,

            @NotBlank(message = "O nome  é obrigatório")
            @Size(max = 50, message = "O nome deve ter no máximo 50 carcteres")
            String nome,

            @NotBlank(message = "O chefe é obrigatório")
            @Size(max = 7, message = "O chefe deve ter no máximo 7 caracteres.")
            String chefe,

            @NotNull(message = "O orçamento é obrigatório")
            BigDecimal orcamento,

            @NotNull(message = "A comissal é obrigatória")
            BigDecimal comissal
    ){}

    record Response(
            String mongoId,
            String cod_depto,
            String nome,
            String chefe,
            BigDecimal orcamento,
            BigDecimal comissal
    ){}

}