package com.projetofinalpoo.models;

/**
 * Representa uma rota de patrulhamento, com informações de nome, bairro,
 * descrição e observações adicionais.
 */
public class Rota {

    private Integer id;
    private String nome;
    private String bairro;
    private String descricao;
    private String observacao;

    /**
     * Construtor da classe Rota.
     *
     * @param id         identificador único da rota
     * @param nome       nome da rota
     * @param bairro     bairro onde a rota está localizada
     * @param descricao  breve descrição da rota
     * @param observacao observações adicionais sobre a rota
     */
    public Rota(Integer id, String nome, String bairro, String descricao, String observacao) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.descricao = descricao;
        this.observacao = observacao;
    }

    /**
     * Retorna o identificador da rota.
     *
     * @return o id da rota
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna o nome da rota.
     *
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da rota.
     *
     * @param nome o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o bairro da rota.
     *
     * @return o bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o bairro da rota.
     *
     * @param bairro o novo bairro
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Retorna a descrição da rota.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da rota.
     *
     * @param descricao a nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna observações adicionais sobre a rota.
     *
     * @return a observação
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * Define observações adicionais sobre a rota.
     *
     * @param observacao a nova observação
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * Gera o código hash da rota com base em seus atributos.
     *
     * @return o valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
        return result;
    }

    /**
     * Verifica se duas rotas são iguais com base nos seus atributos (exceto o ID).
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
        Rota other = (Rota) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (bairro == null) {
            if (other.bairro != null)
                return false;
        } else if (!bairro.equals(other.bairro))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (observacao == null) {
            if (other.observacao != null)
                return false;
        } else if (!observacao.equals(other.observacao))
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string da rota.
     *
     * @return uma string com os dados da rota
     */
    @Override
    public String toString() {
        return "Rota [nome=" + nome + ", bairro=" + bairro + ", descricao=" + descricao + ", observacao=" + observacao
                + "]";
    }
}