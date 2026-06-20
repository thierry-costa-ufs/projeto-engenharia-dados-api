package com.ufs.engdados.domain.vinculo.dto;

public class VinculoDTO {
    public record Request(
            String matEstudante,
            Long codCurso,
            String semestre,
            String situacao
    ) {}

    public record Response(
            Long id,
            String mongoId,
            String matEstudante,
            Long codCurso,
            String semestre,
            String situacao,
            String statusExecucao
    ) {}
}