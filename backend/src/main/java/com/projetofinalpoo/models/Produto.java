package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Representa um produto (ex: câmera, alarme, cerca elétrica) instalado em um
 * endereço específico.
 */
public class Produto {
    private int id;

    /** Data da instacação do produto no formato dd/MM/yyyy para JSON. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataInst;

    /** Duração da retirada do produto no formato HH:mm:ss para JSON. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataRet;

    private boolean defeito;
    private int idEndereco;

    /**
     * Construtor vazio
     */
    public Produto() {}
    
    /**
     * Construtor completo da classe Produto.
     *
     * @param id         o identificador do produto
     * @param dataInst   a data de instalação no formato "dd/MM/yyyy" (pode ser nula
     *                   ou vazia)
     * @param dataRet    a data de retirada no formato "dd/MM/yyyy" (pode ser nula
     *                   ou vazia)
     * @param defeito    indica se o produto está com defeito
     * @param idEndereco o ID do endereço onde o produto está/estava instalado
     */
    public Produto(int id, String dataInst, String dataRet, boolean defeito, int idEndereco) {
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataInst = (dataInst != null && !dataInst.isEmpty()) ? LocalDate.parse(dataInst, formatter) : null;
        this.dataRet = (dataRet != null && !dataRet.isEmpty()) ? LocalDate.parse(dataRet, formatter) : null;
        this.defeito = defeito;
        this.idEndereco = idEndereco;
    }

    /**
     * Retorna o identificador do produto.
     *
     * @return o id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do produto.
     *
     * @param id o novo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data de instalação do produto.
     *
     * @return a data de instalação
     */
    public LocalDate getDataInst() {
        return dataInst;
    }

    /**
     * Define a data de instalação do produto.
     *
     * @param dataInst a nova data de instalação
     */
    public void setDataInst(LocalDate dataInst) {
        this.dataInst = dataInst;
    }

    /**
     * Retorna a data de retirada do produto.
     *
     * @return a data de retirada
     */
    public LocalDate getDataRet() {
        return dataRet;
    }

    /**
     * Define a data de retirada do produto.
     *
     * @param dataRet a nova data de retirada
     */
    public void setDataRet(LocalDate dataRet) {
        this.dataRet = dataRet;
    }

    /**
     * Verifica se o produto está com defeito.
     *
     * @return {@code true} se estiver com defeito; {@code false} caso contrário
     */
    public boolean isDefeito() {
        return defeito;
    }

    /**
     * Define o estado de defeito do produto.
     *
     * @param defeito {@code true} se estiver com defeito; {@code false} caso
     *                contrário
     */
    public void setDefeito(boolean defeito) {
        this.defeito = defeito;
    }

    /**
     * Retorna o ID do endereço relacionado ao produto.
     *
     * @return o ID do endereço
     */
    public int getIdEndereco() {
        return idEndereco;
    }

    /**
     * Define o ID do endereço relacionado ao produto.
     *
     * @param idEndereco o novo ID do endereço
     */
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    /**
     * Gera o código hash do objeto Produto.
     *
     * @return o valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((dataInst == null) ? 0 : dataInst.hashCode());
        result = prime * result + ((dataRet == null) ? 0 : dataRet.hashCode());
        result = prime * result + (defeito ? 1231 : 1237);
        result = prime * result + idEndereco;
        return result;
    }

    /**
     * Verifica se dois objetos Produto são iguais com base em seus atributos.
     *
     * @param obj o objeto a ser comparado
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
        Produto other = (Produto) obj;
        if (id != other.id)
            return false;
        if (dataInst == null) {
            if (other.dataInst != null)
                return false;
        } else if (!dataInst.equals(other.dataInst))
            return false;
        if (dataRet == null) {
            if (other.dataRet != null)
                return false;
        } else if (!dataRet.equals(other.dataRet))
            return false;
        if (defeito != other.defeito)
            return false;
        if (idEndereco != other.idEndereco)
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string do produto.
     *
     * @return uma string com os dados do produto
     */
    @Override
    public String toString() {
        return "Produto [id=" + id + ", dataInst=" + dataInst + ", dataRet=" + dataRet + ", defeito=" + defeito
                + ", idEndereco=" + idEndereco + "]";
    }
}
