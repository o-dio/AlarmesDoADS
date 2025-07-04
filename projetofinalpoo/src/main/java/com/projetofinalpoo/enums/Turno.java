package com.projetofinalpoo.enums;

/**
 * Enumeração que representa os turnos de trabalho.
 * Pode ser Diurno (D) ou Noturno (N).
 */
public enum Turno {
    /** Turno diurno. */
    D("Diurno"),
    /** Turno noturno. */
    N("Noturno");

    private final String descricao;

    /**
     * Construtor do enum Turno.
     * 
     * @param descricao descrição do turno.
     */
    Turno(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição do turno.
     * 
     * @return descrição do turno.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a descrição do turno como representação em string.
     * 
     * @return descrição do turno.
     */
    @Override
    public String toString() {
        return descricao;
    }
}