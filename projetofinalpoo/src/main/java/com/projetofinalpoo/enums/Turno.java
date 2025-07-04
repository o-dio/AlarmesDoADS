package com.projetofinalpoo.enums;

public enum Turno {
    D("Diurno"),
    N("Noturno");

    private final String descricao;

    Turno(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}