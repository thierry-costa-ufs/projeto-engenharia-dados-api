package com.ufs.engdados.domain.curso.enums;

public enum TipoGrau {
    BACHARELADO("Bacharelado"),
    LICENCIATURA_PLENA("Licenciatura Plena");

    private final String descricao;

    TipoGrau(String d) { this.descricao = d; }

    public String getDescricao() { return descricao; }

    public static TipoGrau deString(String valor) {
        if (valor == null) return null;
        for (TipoGrau t : values()) {
            if (t.name().equalsIgnoreCase(valor) || t.getDescricao().equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Grau acadêmico inválido: " + valor);
    }
}