package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma ocorrência registrada por um vigilante sobre um determinado produto.
 * Contém informações sobre data, duração, vigilante responsável e produto associado.
 */
public class Ocorrencia {
    private int id;
    private LocalDate data;
    private LocalTime duracao;
    private int idVigilante;
    private int idProduto;

    /**
     * Construtor padrão.
     */
    public Ocorrencia() {
    }

    /**
     * Construtor completo com todos os atributos.
     *
     * @param id          Identificador da ocorrência.
     * @param data        Data da ocorrência no formato "dd/MM/yyyy".
     * @param duracao     Duração da ocorrência no formato "HH:mm:ss".
     * @param idVigilante ID do vigilante responsável.
     * @param idProduto   ID do produto associado.
     */
    public Ocorrencia(int id, String data, String duracao, int idVigilante, int idProduto) {
        this.id = id;
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.idVigilante = idVigilante;
        this.idProduto = idProduto;
    }

    /**
     * Construtor sem o ID, útil para criar ocorrências antes de persistir em banco.
     *
     * @param data        Data da ocorrência no formato "dd/MM/yyyy".
     * @param duracao     Duração da ocorrência no formato "HH:mm:ss".
     * @param idVigilante ID do vigilante responsável.
     * @param idProduto   ID do produto associado.
     */
    public Ocorrencia(String data, String duracao, int idVigilante, int idProduto) {
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.idVigilante = idVigilante;
        this.idProduto = idProduto;
    }

    // Getters e Setters

    /** @return ID da ocorrência. */
    public int getId() {
        return id;
    }

    /** @param id Define o ID da ocorrência. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return Data da ocorrência. */
    public LocalDate getData() {
        return data;
    }

    /** @param data Define a data da ocorrência. */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /** @return Duração da ocorrência. */
    public LocalTime getDuracao() {
        return duracao;
    }

    /** @param duracao Define a duração da ocorrência. */
    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    /** @return ID do vigilante responsável pela ocorrência. */
    public int getIdVigilante() {
        return idVigilante;
    }

    /** @param idVigilante Define o ID do vigilante responsável. */
    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
    }

    /** @return ID do produto associado à ocorrência. */
    public int getIdProduto() {
        return idProduto;
    }

    /** @param idProduto Define o ID do produto associado à ocorrência. */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Gera o código hash baseado nos atributos da ocorrência.
     *
     * @return Código hash da ocorrência.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
        result = prime * result + idVigilante;
        result = prime * result + idProduto;
        return result;
    }

    /**
     * Compara esta ocorrência com outro objeto para verificar igualdade.
     * Duas ocorrências são iguais se todos os seus atributos forem iguais.
     *
     * @param obj Objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais; {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ocorrencia other = (Ocorrencia) obj;
        if (id != other.id)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (duracao == null) {
            if (other.duracao != null)
                return false;
        } else if (!duracao.equals(other.duracao))
            return false;
        if (idVigilante != other.idVigilante)
            return false;
        if (idProduto != other.idProduto)
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string da ocorrência.
     *
     * @return String contendo todos os dados da ocorrência.
     */
    @Override
    public String toString() {
        return "Ocorrencia [id=" + id + ", data=" + data + ", duracao=" + duracao + ", idVigilante=" + idVigilante
                + ", idProduto=" + idProduto + "]";
    }
}
