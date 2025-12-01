package com.projetofinalpoo.dtos;

/**
 * Data Transfer Object (DTO) usado para receber os dados necessários
 * para cadastrar uma nova ocorrência via API REST.
 * 
 * Contém o identificador do produto monitorado e a duração da ocorrência.
 */
public class OcorrenciaCadastroDTO {

    /** Identificador do produto monitorado associado à ocorrência. */
    private int idProduto;

    /** Duração da ocorrência no formato HH:mm:ss. */
    private String duracao;

    /**
     * Retorna o identificador do produto monitorado.
     * 
     * @return idProduto inteiro representando o produto.
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Define o identificador do produto monitorado.
     * 
     * @param idProduto inteiro representando o produto.
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * Retorna a duração da ocorrência.
     * 
     * @return String no formato HH:mm:ss representando a duração.
     */
    public String getDuracao() {
        return duracao;
    }

    /**
     * Define a duração da ocorrência.
     * 
     * @param duracao String no formato HH:mm:ss representando a duração.
     */
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}
