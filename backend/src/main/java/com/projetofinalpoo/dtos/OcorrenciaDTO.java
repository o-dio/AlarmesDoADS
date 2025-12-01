package com.projetofinalpoo.dtos;

/**
 * DTO (Data Transfer Object) para receber os dados de cadastro de uma ocorrência.
 */
public class OcorrenciaDTO {

    /** Identificador do produto monitorado */
    private int idProduto;

    /** Duração da ocorrência no formato HH:mm:ss */
    private String duracao;

    /**
     * Construtor padrão necessário para a desserialização do JSON recebido pelo frontend.
     */
    public OcorrenciaDTO() {
    }

    /**
     * Construtor completo para facilitar a criação manual do DTO.
     *
     * @param idProduto Identificador do produto monitorado.
     * @param duracao Duração da ocorrência no formato HH:mm:ss.
     */
    public OcorrenciaDTO(int idProduto, String duracao) {
        this.idProduto = idProduto;
        this.duracao = duracao;
    }

    /**
     * Retorna o identificador do produto monitorado.
     *
     * @return ID do produto.
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Define o identificador do produto monitorado.
     *
     * @param idProduto ID do produto.
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Retorna a duração da ocorrência.
     *
     * @return Duração no formato HH:mm:ss.
     */
    public String getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da ocorrência.
     *
     * @param duracao Duração no formato HH:mm:ss.
     */
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}
