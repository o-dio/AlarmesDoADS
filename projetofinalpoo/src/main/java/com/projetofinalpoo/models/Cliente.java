package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.projetofinalpoo.services.HashMD5Service;

/**
 * Representa um cliente do sistema.
 * Implementa a interface {@code Usuario} e armazena informações pessoais e de contato.
 */
public class Cliente implements Usuario {
    // Atributos
    private String login;
    private String senha;
    private String cpf;
    private LocalDate dataNasc;
    private ContatoInfo contatoInfo;

    /**
     * Construtor padrão. Inicializa um cliente vazio.
     */
    public Cliente() {}

    /**
     * Construtor que inicializa o cliente com todos os dados.
     *
     * @param login       o login do cliente
     * @param senha       a senha do cliente (texto puro)
     * @param cpf         o CPF do cliente
     * @param dataNasc    a data de nascimento no formato "dd/MM/yyyy"
     * @param fone        o telefone principal
     * @param email       o email do cliente
     * @param foneContato o telefone de contato alternativo
     */
    public Cliente(String login, String senha, String cpf, String dataNasc, ContatoInfo contatoInfo) {
        super();
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNasc = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.contatoInfo = contatoInfo;
    }

    /**
     * Retorna o login do cliente.
     *
     * @return o login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do cliente.
     *
     * @param login o novo login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retorna a senha do cliente (criptografada).
     *
     * @return a senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do cliente aplicando criptografia MD5.
     *
     * @param senha a nova senha em texto puro
     */
    public void setSenha(String senha){
        this.senha = HashMD5Service.gerarMD5(senha);
    }

    /**
     * Retorna o CPF do cliente.
     *
     * @return o CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     *
     * @param cpf o novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna a data de nascimento do cliente.
     *
     * @return a data de nascimento
     */
    public LocalDate getDataNasc() {
        return dataNasc;
    }

    /**
     * Define a data de nascimento do cliente.
     *
     * @param dataNasc a nova data de nascimento
     */
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public ContatoInfo getContatoInfo() {
        return contatoInfo;
    }

    public void setContatoInfo(ContatoInfo contatoInfo) {
        this.contatoInfo = contatoInfo;
    }

    /**
     * Gera o código hash com base nos atributos do cliente.
     *
     * @return o valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((dataNasc == null) ? 0 : dataNasc.hashCode());
        return result;
    }

    /**
     * Compara este cliente com outro para verificar igualdade.
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
        Cliente other = (Cliente) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (senha == null) {
            if (other.senha != null)
                return false;
        } else if (!senha.equals(other.senha))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (dataNasc == null) {
            if (other.dataNasc != null)
                return false;
        } else if (!dataNasc.equals(other.dataNasc))
            return false;
        return true;
    }

    /**
     * Retorna uma representação em string do objeto Cliente.
     *
     * @return uma string com os dados do cliente
     */
    @Override
    public String toString() {
        return "Cliente [login=" + login + ", senha=" + senha + ", cpf=" + cpf + ", dataNasc=" + dataNasc
                + ", contatoInfo=" + contatoInfo + "]";
    }
}
