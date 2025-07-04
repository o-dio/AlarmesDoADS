package com.projetofinalpoo.models;

public class ContatoInfo {
    //Atributos
    private String fone;
    private String email;
    private String foneContato;

    //Construtor
    public ContatoInfo(String fone, String email, String foneContato) {
        this.fone = fone;
        this.email = email;
        this.foneContato = foneContato;
    }

    //Metodos
    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoneContato() {
        return foneContato;
    }

    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fone == null) ? 0 : fone.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((foneContato == null) ? 0 : foneContato.hashCode());
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
        ContatoInfo other = (ContatoInfo) obj;
        if (fone == null) {
            if (other.fone != null)
                return false;
        } else if (!fone.equals(other.fone))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (foneContato == null) {
            if (other.foneContato != null)
                return false;
        } else if (!foneContato.equals(other.foneContato))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContatoInfo [fone=" + fone + ", email=" + email + ", foneContato=" + foneContato + "]";
    }
    
}
