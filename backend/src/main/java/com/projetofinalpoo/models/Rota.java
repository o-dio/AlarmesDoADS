package com.projetofinalpoo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma rota de patrulhamento, com informações de nome, bairro,
 * descrição e observações adicionais.
 * Cada rota pode conter múltiplos {@link Endereco}.
 */
public class Rota {

    private Integer id;
    private String nome;
    private String bairro;
    private String descricao;
    private String observacao;

    // Lista de endereços que pertencem a esta rota
    private List<Endereco> enderecos;

    /**
     * Construtor da classe Rota.
     *
     * @param id         Identificador único da rota.
     * @param nome       Nome da rota.
     * @param bairro     Bairro onde a rota está localizada.
     * @param descricao  Breve descrição da rota.
     * @param observacao Observações adicionais sobre a rota.
     */
    public Rota(Integer id, String nome, String bairro, String descricao, String observacao) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.descricao = descricao;
        this.observacao = observacao;
        this.enderecos = new ArrayList<>();
    }

    /**
     * Adiciona um endereço à rota e garante a atualização bidirecional.
     *
     * @param endereco Endereço a ser adicionado à rota.
     */
    public void adicionarEndereco(Endereco endereco) {
        if (!enderecos.contains(endereco)) {
            enderecos.add(endereco);
            endereco.adicionarRota(this);
        }
    }

    /**
     * Remove um endereço da rota e garante a atualização bidirecional.
     *
     * @param endereco Endereço a ser removido da rota.
     */
    public void removerEndereco(Endereco endereco) {
        if (enderecos.contains(endereco)) {
            enderecos.remove(endereco);
            endereco.removerRota(this);
        }
    }

    // Getters e Setters

    /** @return ID da rota. */
    public Integer getId() {
        return id;
    }

    /** @return Nome da rota. */
    public String getNome() {
        return nome;
    }

    /** @param nome Define o nome da rota. */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return Bairro onde a rota está localizada. */
    public String getBairro() {
        return bairro;
    }

    /** @param bairro Define o bairro da rota. */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /** @return Descrição da rota. */
    public String getDescricao() {
        return descricao;
    }

    /** @param descricao Define a descrição da rota. */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /** @return Observações adicionais sobre a rota. */
    public String getObservacao() {
        return observacao;
    }

    /** @param observacao Define observações adicionais da rota. */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /** @return Lista de endereços associados à rota. */
    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    /**
     * Gera o código hash da rota com base em seus atributos (excluindo lista de endereços e ID).
     *
     * @return Valor do hash da rota.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, bairro, descricao, observacao);
    }

    /**
     * Verifica se duas rotas são iguais com base nos seus atributos (exceto o ID e a lista de endereços).
     *
     * @param obj Objeto a ser comparado.
     * @return {@code true} se forem iguais; caso contrário, {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Rota other = (Rota) obj;
        return Objects.equals(nome, other.nome) &&
               Objects.equals(bairro, other.bairro) &&
               Objects.equals(descricao, other.descricao) &&
               Objects.equals(observacao, other.observacao);
    }

    /**
     * Retorna uma representação em string da rota.
     * Inclui os endereços de forma resumida para evitar recursão infinita.
     *
     * @return String contendo os dados da rota e os endereços resumidos.
     */
    @Override
    public String toString() {
        String enderecosStr = "";
        for (Endereco e : enderecos) {
            enderecosStr += e.getRua() + ", " + e.getNumero() + "; ";
        }
        return "Rota [nome=" + nome + ", bairro=" + bairro + ", descricao=" + descricao +
               ", observacao=" + observacao + ", enderecos=[" + enderecosStr + "]]";
    }
}
