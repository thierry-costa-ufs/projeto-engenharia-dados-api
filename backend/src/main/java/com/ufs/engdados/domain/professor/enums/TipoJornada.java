package com.ufs.engdados.domain.professor.enums;

import java.util.Arrays;

public enum TipoJornada {
    VINTE_HORAS("20h"),
    QUARENTA_HORAS("40h"),
    DEDICACAO_EXCLUSIVA("DE");

    private final String codigoDb;

    TipoJornada(String codigoDb) {
        this.codigoDb = codigoDb;
    }

    public String getCodigoDb() {
        return codigoDb;
    }

    public static TipoJornada deString(String texto) {
        return Arrays.stream(TipoJornada.values())
                .filter(j -> j.codigoDb.equalsIgnoreCase(texto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jornada de trabalho inválida: " + texto));
    }
}