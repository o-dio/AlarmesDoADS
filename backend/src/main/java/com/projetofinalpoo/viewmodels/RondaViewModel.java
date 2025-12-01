package com.projetofinalpoo.viewmodels;

/**
 * ViewModel usada para encapsular dados da ronda que serão exibidos na interface.
 * Contém informações sobre datas, localização, descrição, status e rota da ronda.
 */
public class RondaViewModel {

    private String dataIni;
    private String dataFim;
    private String local;
    private String bairro;
    private String descricao;
    private String status;
    private String enderecoCompleto;
    private int idRota;

    /**
     * Construtor responsável por inicializar os dados de uma ronda.
     *
     * @param dataIni        Data de início da ronda.
     * @param dataFim        Data de término da ronda.
     * @param local          Nome do local da ronda.
     * @param bairro         Bairro onde a ronda foi realizada.
     * @param descricao      Descrição detalhada da ronda.
     * @param status         Status atual da ronda.
     * @param enderecoCompleto Endereço completo associado à ronda.
     * @param idRota         Identificador da rota associada à ronda.
     */
    public RondaViewModel(String dataIni, String dataFim,
                          String local, String bairro, String descricao,
                          String status, String enderecoCompleto, int idRota) {
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.local = local;
        this.bairro = bairro;
        this.descricao = descricao;
        this.status = status;
        this.enderecoCompleto = enderecoCompleto;
        this.idRota = idRota;
    }

    /** @return Data de início da ronda. */
    public String getDataIni() {
        return dataIni;
    }

    /** @return Data de término da ronda. */
    public String getDataFim() {
        return dataFim;
    }

    /** @return Nome do local da ronda. */
    public String getLocal() {
        return local;
    }

    /** @return Bairro onde a ronda foi realizada. */
    public String getBairro() {
        return bairro;
    }

    /** @return Descrição detalhada da ronda. */
    public String getDescricao() {
        return descricao;
    }

    /** @return Status atual da ronda. */
    public String getStatus() {
        return status;
    }

    /** @return Endereço completo associado à ronda. */
    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    /** @return Identificador da rota associada à ronda. */
    public int getIdRota() {
        return idRota;
    }
}
