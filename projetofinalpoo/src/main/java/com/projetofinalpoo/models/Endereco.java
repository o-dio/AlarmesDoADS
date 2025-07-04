package com.projetofinalpoo.models;

/**
 * Representa um endereço com informações como rua, número, bairro, cidade, estado e CEP.
 */
public class Endereco {
    private int id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    /**
     * Cria uma instância de Endereco com todos os atributos.
     *
     * @param id o identificador do endereço
     * @param rua o nome da rua
     * @param numero o número do imóvel
     * @param bairro o bairro
     * @param cidade a cidade
     * @param estado o estado
     * @param cep o código postal (CEP)
     */
    public Endereco(){};
    public Endereco(int id, String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    /**
     * Retorna o identificador do endereço.
     *
     * @return o id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do endereço.
     *
     * @param id o novo id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da rua.
     *
     * @return a rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * Define o nome da rua.
     *
     * @param rua a nova rua
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    /**
     * Retorna o número do imóvel.
     *
     * @return o número
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define o número do imóvel.
     *
     * @param numero o novo número
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Retorna o bairro.
     *
     * @return o bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o bairro.
     *
     * @param bairro o novo bairro
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Retorna a cidade.
     *
     * @return a cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade.
     *
     * @param cidade a nova cidade
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Retorna o estado.
     *
     * @return o estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define o estado.
     *
     * @param estado o novo estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna o código postal (CEP).
     *
     * @return o CEP
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o código postal (CEP).
     *
     * @param cep o novo CEP
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Gera o código hash com base nos atributos do endereço.
     *
     * @return o valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((rua == null) ? 0 : rua.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
        result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((cep == null) ? 0 : cep.hashCode());
        return result;
    }

    /**
     * Compara este objeto com outro para verificar igualdade.
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
        Endereco other = (Endereco) obj;
        if (id != other.id)
            return false;
        if (rua == null) {
            if (other.rua != null)
                return false;
        } else if (!rua.equals(other.rua))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (bairro == null) {
            if (other.bairro != null)
                return false;
        } else if (!bairro.equals(other.bairro))
            return false;
        if (cidade == null) {
            if (other.cidade != null)
                return false;
        } else if (!cidade.equals(other.cidade))
            return false;
        if (estado == null) {
            if (other.estado != null)
                return false;
        } else if (!estado.equals(other.estado))
            return false;
        if (cep == null) {
            if (other.cep != null)
                return false;
        } else if (!cep.equals(other.cep))
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string do endereço.
     *
     * @return uma string formatada com os dados do endereço
     */
    @Override
    public String toString() {
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado + ", CEP: " + cep;
    }
}
