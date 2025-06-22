package com.projetofinalpoo.models;

public class Admin {
    //Atributos
    private String login;
	private String senha;
	private String cargo;
    
    //Construtor
    public Admin(String login, String senha, String cargo) {
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
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
        if (cargo == null) {
            if (other.cargo != null)
                return false;
        } else if (!cargo.equals(other.cargo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Admin [login=" + login + ", senha=" + senha + ", cargo=" + cargo + "]";
    } 
}
