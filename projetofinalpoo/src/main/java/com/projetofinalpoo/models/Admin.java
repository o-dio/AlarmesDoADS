package com.projetofinalpoo.models;

import java.security.NoSuchAlgorithmException;

import com.projetofinalpoo.services.HashMD5Service;

public class Admin implements Usuario {
    //Atributos
    private String login;
	private String senha;
    
    //Construtor
    public Admin(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
    
    //Metodos
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws NoSuchAlgorithmException {
        this.senha = HashMD5Service.gerarMD5(senha);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        return result;
    }


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

    @Override
    public String toString() {
        return "Admin [login=" + login + ", senha=" + senha + "]";
    } 
}
