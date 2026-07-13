package com.ufs.engdados.domain.professor.enums;

import java.util.Arrays;

public enum TipoFormacao {
    GRADUACAO("Graduação"),
    ESPECIALIZACAO("Especialização"),
    MESTRADO("Mestrado"),
    DOUTORADO("Doutorado");

    private final String codigoDb;

    TipoFormacao(String codigoDb) {
        this.codigoDb = codigoDb;
    }

    public String getCodigoDb() {
        return codigoDb;
    }

    public static TipoFormacao deString(String texto) {
        return Arrays.stream(TipoFormacao.values())
                .filter(f -> f.codigoDb.equalsIgnoreCase(texto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de formação inválido: " + texto));
    }
}