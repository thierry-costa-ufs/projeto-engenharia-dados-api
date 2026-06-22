package com.ufs.engdados.domain.curso.enums;

public enum TipoTurno {
    MATUTINO("Matutino"),
    VESPERTINO("Vespertino"),
    NOTURNO("Noturno"),
    TURNO_INDEFINIDO("Turno Indefinido");

    private final String descricao;

    TipoTurno(String d) { this.descricao = d; }

    public String getDescricao() { return descricao; }

    public static TipoTurno deString(String valor) {
        if (valor == null) return null;
        for (TipoTurno t : values()) {
            if (t.name().equalsIgnoreCase(valor) || t.getDescricao().equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Turno inválido: " + valor);
    }
}