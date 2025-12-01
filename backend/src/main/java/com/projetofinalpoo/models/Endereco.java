package com.projetofinalpoo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa um endereço físico que pode estar associado a múltiplos {@link Contrato} e {@link Rota}.
 */
public class Endereco {
    private int id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private List<Rota> rotas;
    private List<Contrato> contratos;

    /**
     * Construtor padrão que inicializa as listas de rotas e contratos.
     */
    public Endereco() {
        this.rotas = new ArrayList<>();
        this.contratos = new ArrayList<>();
    }

    /**
     * Construtor que inicializa um endereço com todos os atributos.
     *
     * @param id     Identificador do endereço.
     * @param rua    Nome da rua.
     * @param numero Número do endereço.
     * @param bairro Bairro do endereço.
     * @param cidade Cidade do endereço.
     * @param estado Estado do endereço.
     * @param cep    CEP do endereço.
     */
    public Endereco(int id, String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this();
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    /**
     * Adiciona um contrato ao endereço, garantindo que a relação seja bidirecional.
     *
     * @param contrato Contrato a ser adicionado.
     */
    public void adicionarContrato(Contrato contrato) {
        if (!contratos.contains(contrato)) {
            contratos.add(contrato);
            if (!contrato.getEnderecos().contains(this)) {
                contrato.adicionarEndereco(this);
            }
        }
    }

    /**
     * Remove um contrato do endereço, garantindo que a relação bidirecional seja atualizada.
     *
     * @param contrato Contrato a ser removido.
     */
    public void removerContrato(Contrato contrato) {
        if (contratos.contains(contrato)) {
            contratos.remove(contrato);
            if (contrato.getEnderecos().contains(this)) {
                contrato.removerEndereco(this);
            }
        }
    }

    /**
     * Adiciona uma rota ao endereço, garantindo que a relação seja bidirecional.
     *
     * @param rota Rota a ser adicionada.
     */
    public void adicionarRota(Rota rota) {
        if (!rotas.contains(rota)) {
            rotas.add(rota);
            if (!rota.getEnderecos().contains(this)) {
                rota.adicionarEndereco(this);
            }
        }
    }

    /**
     * Remove uma rota do endereço, garantindo que a relação bidirecional seja atualizada.
     *
     * @param rota Rota a ser removida.
     */
    public void removerRota(Rota rota) {
        if (rotas.contains(rota)) {
            rotas.remove(rota);
            if (rota.getEnderecos().contains(this)) {
                rota.removerEndereco(this);
            }
        }
    }

    // Getters e Setters com JavaDoc

    /** @return Identificador do endereço. */
    public int getId() { 
        return id; 
    }

    /** @param id Define o identificador do endereço. */
    public void setId(int id) { 
        this.id = id; 
    }

    /** @return Nome da rua do endereço. */
    public String getRua() { 
        return rua; 
    }

    /** @param rua Define o nome da rua do endereço. */
    public void setRua(String rua) { 
        this.rua = rua; 
    }

    /** @return Número do endereço. */
    public String getNumero() { 
        return numero; 
    }

    /** @param numero Define o número do endereço. */
    public void setNumero(String numero) { 
        this.numero = numero; 
    }

    /** @return Bairro do endereço. */
    public String getBairro() { 
        return bairro; 
    }

    /** @param bairro Define o bairro do endereço. */
    public void setBairro(String bairro) { 
        this.bairro = bairro; 
    }

    /** @return Cidade do endereço. */
    public String getCidade() { 
        return cidade; 
    }

    /** @param cidade Define a cidade do endereço. */
    public void setCidade(String cidade) { 
        this.cidade = cidade; 
    }

    /** @return Estado do endereço. */
    public String getEstado() { 
        return estado; 
    }

    /** @param estado Define o estado do endereço. */
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    /** @return CEP do endereço. */
    public String getCep() { 
        return cep; 
    }

    /** @param cep Define o CEP do endereço. */
    public void setCep(String cep) { 
        this.cep = cep; 
    }

    /** @return Lista de rotas associadas ao endereço. */
    public List<Rota> getRotas() { 
        return rotas; 
    }

    /** @return Lista de contratos associados ao endereço. */
    public List<Contrato> getContratos() { 
        return contratos; 
    }

    /**
     * Retorna o código hash do endereço, baseado em id, rua, número, bairro, cidade, estado e CEP.
     *
     * @return Código hash do endereço.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, rua, numero, bairro, cidade, estado, cep);
    }

    /**
     * Compara este endereço com outro objeto para verificar igualdade.
     * Dois endereços são considerados iguais se tiverem o mesmo id, rua, número, bairro, cidade, estado e CEP.
     *
     * @param obj Objeto a ser comparado.
     * @return true se os endereços forem iguais; false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Endereco other = (Endereco) obj;
        return id == other.id &&
               Objects.equals(rua, other.rua) &&
               Objects.equals(numero, other.numero) &&
               Objects.equals(bairro, other.bairro) &&
               Objects.equals(cidade, other.cidade) &&
               Objects.equals(estado, other.estado) &&
               Objects.equals(cep, other.cep);
    }

    /**
     * Retorna uma representação em String do endereço, incluindo rua, número, bairro, cidade, estado, CEP,
     * rotas associadas e contratos associados.
     *
     * @return Representação em String do endereço.
     */
    @Override
    public String toString() {
        String rotasStr = rotas.isEmpty() ? "Nenhuma" : rotas.toString();
        String contratosStr = contratos.isEmpty() ? "Nenhum" : contratos.toString();
        return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado +
               ", CEP: " + cep + ", rotas=[" + rotasStr + "], contratos=[" + contratosStr + "]";
    }
}
