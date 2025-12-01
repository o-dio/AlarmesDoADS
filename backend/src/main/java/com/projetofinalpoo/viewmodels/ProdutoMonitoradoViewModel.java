package com.projetofinalpoo.viewmodels;

/**
 * ViewModel utilizada para transportar informações de um produto monitorado
 * e seu cliente associado, incluindo contato e endereço completo.
 */
public class ProdutoMonitoradoViewModel {

    private int idProduto;
    private String nomeCliente;
    private String telefoneCliente;
    private String enderecoCompleto;

    /**
     * Construtor responsável por inicializar um objeto contendo os dados do produto
     * monitorado e do cliente associado.
     *
     * @param idProduto       Identificador do produto monitorado.
     * @param nomeCliente     Nome do cliente proprietário do produto.
     * @param telefoneCliente Telefone de contato do cliente.
     * @param enderecoCompleto Endereço completo do cliente.
     */
    public ProdutoMonitoradoViewModel(int idProduto, String nomeCliente,
                                      String telefoneCliente, String enderecoCompleto) {

        this.idProduto = idProduto;
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
        this.enderecoCompleto = enderecoCompleto;
    }

    /** @return Identificador do produto monitorado. */
    public int getIdProduto() { 
        return idProduto; 
    }

    /** @return Nome do cliente proprietário do produto monitorado. */
    public String getNomeCliente() { 
        return nomeCliente; 
    }

    /** @return Telefone de contato do cliente. */
    public String getTelefoneCliente() { 
        return telefoneCliente; 
    }

    /** @return Endereço completo do cliente. */
    public String getEnderecoCompleto() { 
        return enderecoCompleto; 
    }
}
