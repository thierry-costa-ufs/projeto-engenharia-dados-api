package com.ufs.engdados.domain.curso.enums;

public enum TipoNivel {
    GRADUACAO("Graduação"),
    MESTRADO("Mestrado"),
    DOUTORADO("Doutorado"),
    LATO("Lato");

    private final String descricao;

    TipoNivel(String d) { this.descricao = d; }

    public String getDescricao() { return descricao; }

    public static TipoNivel deString(String valor) {
        if (valor == null) return null;
        for (TipoNivel t : values()) {
            if (t.name().equalsIgnoreCase(valor) || t.getDescricao().equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Nível de curso inválido: " + valor);
    }
}