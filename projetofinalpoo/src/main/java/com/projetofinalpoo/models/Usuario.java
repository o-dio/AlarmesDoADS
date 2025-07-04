package com.projetofinalpoo.models;

import java.security.NoSuchAlgorithmException;

public interface Usuario {

    public String getLogin();

    public void setLogin(String login);
    public void setSenha(String Senha) throws NoSuchAlgorithmException;
    
}