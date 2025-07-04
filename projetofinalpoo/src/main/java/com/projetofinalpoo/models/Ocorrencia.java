package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma ocorrência registrada por um vigilante sobre um determinado produto.
 */
public class Ocorrencia {
    private int id;
    private LocalDate data;
    private LocalTime duracao;
    private int idVigilante;
    private int idProduto;

    //Construtor
    public Ocorrencia(){}
    public Ocorrencia(int id, String data, String duracao, int idVigilante, int idProduto) {
        this.id = id;
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.idVigilante = idVigilante;
        this.idProduto = idProduto;
    }
public Ocorrencia(String data, String duracao, int idVigilante, int idProduto) {
      
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.idVigilante = idVigilante;
        this.idProduto = idProduto;
    }
    //Metodos
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da ocorrência.
     *
     * @param id o novo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data da ocorrência.
     *
     * @return a data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data da ocorrência.
     *
     * @param data a nova data
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Retorna a duração da ocorrência.
     *
     * @return a duração
     */
    public LocalTime getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da ocorrência.
     *
     * @param duracao a nova duração
     */
    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna o ID do vigilante responsável pela ocorrência.
     *
     * @return o ID do vigilante
     */
    public int getIdVigilante() {
        return idVigilante;
    }

    /**
     * Define o ID do vigilante responsável pela ocorrência.
     *
     * @param idVigilante o novo ID do vigilante
     */
    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
    }

    /**
     * Retorna o ID do produto associado à ocorrência.
     *
     * @return o ID do produto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Define o ID do produto associado à ocorrência.
     *
     * @param idProduto o novo ID do produto
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Gera o código hash baseado nos atributos da ocorrência.
     *
     * @return o valor do hash
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
     * Compara esta ocorrência com outra para verificar igualdade.
     *
     * @param obj o objeto a ser comparado
     * @return {@code true} se os objetos forem iguais; caso contrário, {@code false}
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
     * @return uma string com os dados da ocorrência
     */
    @Override
    public String toString() {
        return "Ocorrencia [id=" + id + ", data=" + data + ", duracao=" + duracao + ", idVigilante=" + idVigilante
                + ", idProduto=" + idProduto + "]";
    }
}
