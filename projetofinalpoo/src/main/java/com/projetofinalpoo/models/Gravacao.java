package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa uma gravação relacionada a um produto, contendo informações como
 * data, duração e arquivo.
 */
public class Gravacao {
    private int id;
    private LocalDate data;
    private LocalTime duracao;
    private String arquivo;
    private String descricao;
    private int idProduto;

    /**
     * Construtor vazio
     */
    public Gravacao() {
    }

    /**
     * Cria uma instância de Gravacao com os dados informados.
     *
     * @param id        o identificador da gravação
     * @param data      a data da gravação no formato "dd/MM/yyyy"
     * @param duracao   a duração da gravação no formato "HH:mm:ss"
     * @param arquivo   o nome ou caminho do arquivo da gravação
     * @param descricao a descrição da gravação
     * @param idProduto o identificador do produto associado
     */
  

    public Gravacao(int id, LocalDate data, LocalTime duracao, String arquivo, String descricao, int idProduto) {
        this.id = id;
        this.data = data;
        this.duracao = duracao;
        this.arquivo = arquivo;
        this.descricao = descricao;
        this.idProduto = idProduto;
    }

    /**
     * Retorna o identificador da gravação.
     *
     * @return o id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da gravação.
     *
     * @param id o novo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data da gravação.
     *
     * @return a data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data da gravação.
     *
     * @param data a nova data
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Retorna a duração da gravação.
     *
     * @return a duração
     */
    public LocalTime getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da gravação.
     *
     * @param duracao a nova duração
     */
    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna o nome ou caminho do arquivo da gravação.
     *
     * @return o arquivo
     */
    public String getArquivo() {
        return arquivo;
    }

    /**
     * Define o nome ou caminho do arquivo da gravação.
     *
     * @param arquivo o novo arquivo
     */
    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    /**
     * Retorna a descrição da gravação.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da gravação.
     *
     * @param descricao a nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o identificador do produto associado à gravação.
     *
     * @return o id do produto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Define o identificador do produto associado à gravação.
     *
     * @param idProduto o novo id do produto
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Gera o código hash com base nos atributos da gravação.
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
        result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + idProduto;
        return result;
    }

    /**
     * Compara este objeto com outro para verificar igualdade.
     *
     * @param obj o objeto a ser comparado
     * @return {@code true} se os objetos forem iguais; caso contrário,
     *         {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gravacao other = (Gravacao) obj;
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
        if (arquivo == null) {
            if (other.arquivo != null)
                return false;
        } else if (!arquivo.equals(other.arquivo))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (idProduto != other.idProduto)
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string da gravação.
     *
     * @return uma string com os dados da gravação
     */
    @Override
    public String toString() {
        return "Gravacao [id=" + id + ", data=" + data + ", duracao=" + duracao + ", arquivo=" + arquivo
                + ", descricao=" + descricao + ", idProduto=" + idProduto + "]";
    }
}
