package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa um contrato de serviço de um cliente.
 * Um contrato depende obrigatoriamente de um {@link Cliente} e pode estar associado a múltiplos {@link Endereco}.
 */

public class Contrato {

    private int id;
    private Cliente cliente;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String formaPagamento;
    private double valor;
    private int parcelas;
    private String status;
    private String contrato;

    private List<Endereco> enderecos;

    /**
     * Construtor padrão que inicializa a lista de endereços.
     */
    public Contrato() {
        this.enderecos = new ArrayList<>();
    }

    /**
     * Construtor que inicializa um contrato com todos os atributos.
     *
     * @param cliente        O cliente associado ao contrato.
     * @param dataInicio     Data de início do contrato no formato "dd/MM/yyyy".
     * @param dataFim        Data de término do contrato no formato "dd/MM/yyyy".
     * @param formaPagamento Forma de pagamento do contrato.
     * @param valor          Valor total do contrato.
     * @param parcelas       Quantidade de parcelas do contrato.
     * @param status         Status atual do contrato.
     * @param contrato       Identificador ou descrição do contrato.
     */
    public Contrato(Cliente cliente, String dataInicio, String dataFim, String formaPagamento,
                    double valor, int parcelas, String status, String contrato) {
        this();
        this.cliente = cliente;
        this.dataInicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.parcelas = parcelas;
        this.status = status;
        this.contrato = contrato;
    }

    /**
     * Adiciona um endereço ao contrato, garantindo que a relação seja bidirecional.
     *
     * @param endereco Endereço a ser adicionado.
     */
    public void adicionarEndereco(Endereco endereco) {
        if (!enderecos.contains(endereco)) {
            enderecos.add(endereco);
            if (!endereco.getContratos().contains(this)) {
                endereco.adicionarContrato(this);
            }
        }
    }

    /**
     * Remove um endereço do contrato, garantindo que a relação bidirecional seja atualizada.
     *
     * @param endereco Endereço a ser removido.
     */
    public void removerEndereco(Endereco endereco) {
        if (enderecos.contains(endereco)) {
            enderecos.remove(endereco);
            if (endereco.getContratos().contains(this)) {
                endereco.removerContrato(this);
            }
        }
    }

    // Getters e Setters com JavaDoc
    

    /** @return O cliente associado ao contrato. */
    public Cliente getCliente() { 
        return cliente; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /** @param cliente Define o cliente associado ao contrato. */
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }

    /** @return Data de início do contrato. */
    public LocalDate getDataInicio() { 
        return dataInicio; 
    }
    
    /** @param dataInicio Define a data de início do contrato. */
    public void setDataInicio(LocalDate dataInicio) { 
        this.dataInicio = dataInicio; 
    }

    /** @return Data de término do contrato. */
    public LocalDate getDataFim() { 
        return dataFim; 
    }

    /** @param dataFim Define a data de término do contrato. */
    public void setDataFim(LocalDate dataFim) { 
        this.dataFim = dataFim; 
    }

    /** @return Forma de pagamento do contrato. */
    public String getFormaPagamento() { 
        return formaPagamento; 
    }

    /** @param formaPagamento Define a forma de pagamento do contrato. */
    public void setFormaPagamento(String formaPagamento) { 
        this.formaPagamento = formaPagamento; 
    }

    /** @return Valor total do contrato. */
    public double getValor() { 
        return valor; 
    }

    /** @param valor Define o valor total do contrato. */
    public void setValor(double valor) { 
        this.valor = valor; 
    }

    /** @return Número de parcelas do contrato. */
    public int getParcelas() { 
        return parcelas; 
    }

    /** @param parcelas Define o número de parcelas do contrato. */
    public void setParcelas(int parcelas) { 
        this.parcelas = parcelas; 
    }

    /** @return Status atual do contrato. */
    public String getStatus() { 
        return status; 
    }

    /** @param status Define o status atual do contrato. */
    public void setStatus(String status) { 
        this.status = status; 
    }

    /** @return Identificador ou descrição do contrato. */
    public String getContrato() { 
        return contrato; 
    }

    /** @param contrato Define o identificador ou descrição do contrato. */
    public void setContrato(String contrato) { 
        this.contrato = contrato; 
    }

    /** @return Lista de endereços associados ao contrato. */
    public List<Endereco> getEnderecos() { 
        return enderecos; 
    }

    /**
     * Retorna o código hash do contrato, baseado no CPF do cliente, datas, forma de pagamento,
     * valor, parcelas, status e identificador do contrato.
     *
     * @return Código hash do contrato.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cliente != null ? cliente.getCpf() : null, dataInicio, dataFim, formaPagamento, valor, parcelas, status, contrato);
    }

    /**
     * Compara este contrato com outro objeto para verificar igualdade.
     * Dois contratos são considerados iguais se tiverem o mesmo cliente (CPF), datas, forma de pagamento,
     * valor, número de parcelas, status e identificador do contrato.
     *
     * @param obj Objeto a ser comparado.
     * @return true se os contratos forem iguais; false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contrato other = (Contrato) obj;
        return Double.compare(other.valor, valor) == 0 &&
               parcelas == other.parcelas &&
               Objects.equals(cliente != null ? cliente.getCpf() : null,
                              other.cliente != null ? other.cliente.getCpf() : null) &&
               Objects.equals(dataInicio, other.dataInicio) &&
               Objects.equals(dataFim, other.dataFim) &&
               Objects.equals(formaPagamento, other.formaPagamento) &&
               Objects.equals(status, other.status) &&
               Objects.equals(contrato, other.contrato);
    }

    /**
     * Retorna uma representação em String do contrato, incluindo CPF do cliente,
     * datas, forma de pagamento, valor, parcelas, status, identificador do contrato
     * e os endereços associados.
     *
     * @return Representação em String do contrato.
     */
    @Override
    public String toString() {
        String clienteStr = (cliente != null) ? cliente.getCpf() : "N/A";
        String enderecosStr = enderecos.isEmpty() ? "Nenhum" : enderecos.toString();
        return "Contrato [clienteCPF=" + clienteStr +
               ", dataInicio=" + dataInicio + ", dataFim=" + dataFim +
               ", formaPagamento=" + formaPagamento + ", valor=" + valor +
               ", parcelas=" + parcelas + ", status=" + status +
               ", contrato=" + contrato + ", enderecos=" + enderecosStr + "]";
    }
}
