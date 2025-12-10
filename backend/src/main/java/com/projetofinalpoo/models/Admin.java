package com.projetofinalpoo.models;

import com.projetofinalpoo.services.HashMD5Service;

/**
 * Representa um usuário do tipo administrador no sistema.
 * Implementa a interface {@code Usuario}.
 */
public class Admin implements Usuario {
    // Atributos
    private String login;
    private String senha;

    /**
     * Cria uma instância de Admin sem login e senha.
     */
    public Admin() {}

    /**
     * Cria uma instância de Admin com login e senha informados.
     *
     * @param login o nome de login do administrador
     * @param senha a senha do administrador (ainda não criptografada)
     */
    public Admin(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    /**
     * Retorna o login do administrador.
     *
     * @return o login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do administrador.
     *
     * @param login o novo login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retorna a senha do administrador.
     *
     * @return a senha (criptografada)
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do administrador após aplicar criptografia MD5.
     *
     * @param senha a nova senha em texto puro
     */
    public void setSenha(String senha) {
        this.senha = HashMD5Service.gerarMD5(senha);
    }

    /**
     * Gera o código hash com base nos atributos login e senha.
     *
     * @return o valor do hash
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        return result;
    }

    /**
     * Compara este objeto com outro para verificar igualdade.
     *
     * @param obj o objeto a ser comparado
     * @return {@code true} se os objetos forem iguais; caso contrário,
     *         {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
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
        return true;
    }

    /**
     * Retorna uma representação em string do objeto Admin.
     *
     * @return uma string com login e senha
     */
    @Override
    public String toString() {
        return "Admin [login=" + login + ", senha=" + senha + "]";
    }
}
