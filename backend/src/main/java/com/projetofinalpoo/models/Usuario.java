package com.projetofinalpoo.models;

/**
 * Interface que define os métodos básicos de um usuário do sistema.
 */
public interface Usuario {

    /**
     * Retorna o login do usuário.
     *
     * @return login como {@code String}
     */
    String getLogin();

    /**
     * Define o login do usuário.
     *
     * @param login novo login
     */
    void setLogin(String login);

    /**
     * Define a senha do usuário. A senha deve ser processada (ex: criptografada).
     *
     * @param senha nova senha em texto plano
     */
    void setSenha(String senha);
}
