package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa um trajeto realizado por um vigilante em uma determinada rota,
 * com datas de início e fim do trajeto.
 */
public class Trajeto {
    // Atributos
    private int id;
    private LocalDate dataIni;
    private LocalDate dataFim;
    private int idVigilante;
    private int idRota;

    /**
     * Construtor padrão da classe Trajeto.
     */
    public Trajeto() {}

    /**
     * Construtor para criação de um trajeto sem ID.
     *
     * @param dataIni     data de início do trajeto no formato "dd/MM/yyyy"
     * @param dataFim     data de término do trajeto no formato "dd/MM/yyyy" (pode ser null)
     * @param idVigilante identificador do vigilante responsável
     * @param idRota      identificador da rota associada
     */
    public Trajeto(String dataIni, String dataFim, int idVigilante, int idRota) {
        this.dataIni = LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dataFim != null) {
            this.dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.dataFim = null;
        }
        this.idVigilante = idVigilante;
        this.idRota = idRota;
    }

    /**
     * Construtor para criação de um trajeto com ID.
     *
     * @param id          identificador do trajeto
     * @param dataIni     data de início do trajeto no formato "dd/MM/yyyy"
     * @param dataFim     data de término do trajeto no formato "dd/MM/yyyy" (pode ser null)
     * @param idVigilante identificador do vigilante responsável
     * @param idRota      identificador da rota associada
     */
    public Trajeto(int id, String dataIni, String dataFim, int idVigilante, int idRota) {
        this.id = id;
        this.dataIni = LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dataFim != null) {
            this.dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.dataFim = null;
        }
        this.idVigilante = idVigilante;
        this.idRota = idRota;
    }

    /**
     * Retorna o ID do trajeto.
     *
     * @return o ID
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do trajeto.
     *
     * @param id novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data de início do trajeto.
     *
     * @return data de início
     */
    public LocalDate getDataIni() {
        return dataIni;
    }

    /**
     * Define a data de início do trajeto.
     *
     * @param dataIni nova data de início
     */
    public void setDataIni(LocalDate dataIni) {
        this.dataIni = dataIni;
    }

    /**
     * Retorna a data de fim do trajeto.
     *
     * @return data de fim (pode ser null)
     */
    public LocalDate getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de fim do trajeto.
     *
     * @param dataFim nova data de fim (pode ser null)
     */
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Retorna o ID do vigilante associado ao trajeto.
     *
     * @return ID do vigilante
     */
    public int getIdVigilante() {
        return idVigilante;
    }

    /**
     * Define o ID do vigilante associado ao trajeto.
     *
     * @param idVigilante novo ID do vigilante
     */
    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
    }

    /**
     * Retorna o ID da rota associada ao trajeto.
     *
     * @return ID da rota
     */
    public int getIdRota() {
        return idRota;
    }

    /**
     * Define o ID da rota associada ao trajeto.
     *
     * @param idRota novo ID da rota
     */
    public void setIdRota(int idRota) {
        this.idRota = idRota;
    }

    /**
     * Gera o código hash do trajeto com base em seus atributos.
     *
     * @return valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataIni == null) ? 0 : dataIni.hashCode());
        result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
        result = prime * result + idVigilante;
        result = prime * result + idRota;
        return result;
    }

    /**
     * Verifica se dois trajetos são iguais com base nos seus atributos.
     *
     * @param obj objeto a ser comparado
     * @return {@code true} se forem iguais; caso contrário, {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Trajeto other = (Trajeto) obj;
        if (dataIni == null) {
            if (other.dataIni != null)
                return false;
        } else if (!dataIni.equals(other.dataIni))
            return false;
        if (dataFim == null) {
            if (other.dataFim != null)
                return false;
        } else if (!dataFim.equals(other.dataFim))
            return false;
        if (idVigilante != other.idVigilante)
            return false;
        if (idRota != other.idRota)
            return false;
        return true;
    }

    /**
     * Retorna uma representação textual do trajeto.
     *
     * @return string com informações do trajeto
     */
    @Override
    public String toString() {
        return "Trajeto [dataIni=" + dataIni + ", dataFim=" + dataFim + ", idVigilante=" + idVigilante + ", idRota=" + idRota + "]";
    }
}
