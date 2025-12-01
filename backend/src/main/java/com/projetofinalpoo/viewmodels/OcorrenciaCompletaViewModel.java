package com.projetofinalpoo.viewmodels;

/**
 * ViewModel utilizada para transportar informações completas de uma ocorrência,
 * combinando dados do produto monitorado, cliente, endereço, vigilante e os
 * detalhes específicos da ocorrência.
 */
public class OcorrenciaCompletaViewModel {

    private int idOcorrencia;
    private int idProduto;
    private String nomeCliente;
    private String telefoneCliente;
    private String enderecoCompleto;
    private String duracao;
    private String data;
    private String vigilante;

    /**
     * Construtor responsável por inicializar um objeto contendo os dados completos
     * de uma ocorrência, combinando informações de várias entidades relacionadas.
     *
     * @param idOcorrencia     Identificador único da ocorrência.
     * @param idProduto        Identificador do produto monitorado relacionado.
     * @param nomeCliente      Nome do cliente proprietário do produto monitorado.
     * @param telefoneCliente  Telefone de contato do cliente.
     * @param enderecoCompleto Endereço completo do cliente.
     * @param duracao          Duração total da ocorrência (formato HH:mm:ss).
     * @param data             Data em que a ocorrência foi registrada.
     * @param vigilante        Nome ou login do vigilante responsável pela ocorrência.
     */
    public OcorrenciaCompletaViewModel(
            int idOcorrencia, int idProduto,
            String nomeCliente, String telefoneCliente,
            String enderecoCompleto,
            String duracao, String data,
            String vigilante
    ) {
        this.idOcorrencia = idOcorrencia;
        this.idProduto = idProduto;
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
        this.enderecoCompleto = enderecoCompleto;
        this.duracao = duracao;
        this.data = data;
        this.vigilante = vigilante;
    }

    /** @return Identificador único da ocorrência. */
    public int getIdOcorrencia() {
        return idOcorrencia;
    }

    /** @param idOcorrencia Define o identificador único da ocorrência. */
    public void setIdOcorrencia(int idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    /** @return Identificador do produto monitorado relacionado. */
    public int getIdProduto() {
        return idProduto;
    }

    /** @param idProduto Define o identificador do produto monitorado relacionado. */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /** @return Nome do cliente proprietário do produto monitorado. */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /** @param nomeCliente Define o nome do cliente proprietário do produto monitorado. */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /** @return Telefone de contato do cliente. */
    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    /** @param telefoneCliente Define o telefone de contato do cliente. */
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    /** @return Endereço completo do cliente. */
    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    /** @param enderecoCompleto Define o endereço completo do cliente. */
    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    /** @return Duração total da ocorrência (formato HH:mm:ss). */
    public String getDuracao() {
        return duracao;
    }

    /** @param duracao Define a duração total da ocorrência (formato HH:mm:ss). */
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    /** @return Data em que a ocorrência foi registrada. */
    public String getData() {
        return data;
    }

    /** @param data Define a data em que a ocorrência foi registrada. */
    public void setData(String data) {
        this.data = data;
    }

    /** @return Nome ou login do vigilante responsável pela ocorrência. */
    public String getVigilante() {
        return vigilante;
    }

    /** @param vigilante Define o nome ou login do vigilante responsável pela ocorrência. */
    public void setVigilante(String vigilante) {
        this.vigilante = vigilante;
    }
}
