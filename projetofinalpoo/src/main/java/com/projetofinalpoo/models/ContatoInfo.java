package com.projetofinalpoo.models;

/**
 * Classe que representa as informações de contato de uma entidade.
 * Contém telefone principal, email e telefone para contato emergencial.
 */
public class ContatoInfo {
    // Atributos
    private String fone;
    private String email;
    private String foneContato;

    /**
     * Construtor que inicializa as informações de contato.
     * 
     * @param fone        telefone principal.
     * @param email       endereço de email.
     * @param foneContato telefone para contato emergencial.
     */
    public ContatoInfo(String fone, String email, String foneContato) {
        this.fone = fone;
        this.email = email;
        this.foneContato = foneContato;
    }

    /**
     * Obtém o telefone principal.
     * 
     * @return o telefone principal.
     */
    public String getFone() {
        return fone;
    }

    /**
     * Define o telefone principal.
     * 
     * @param fone o telefone principal a ser definido.
     */
    public void setFone(String fone) {
        this.fone = fone;
    }

    /**
     * Obtém o email.
     * 
     * @return o email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email.
     * 
     * @param email o email a ser definido.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém o telefone para contato emergencial.
     * 
     * @return o telefone de contato emergencial.
     */
    public String getFoneContato() {
        return foneContato;
    }

    /**
     * Define o telefone para contato emergencial.
     * 
     * @param foneContato o telefone de contato emergencial a ser definido.
     */
    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
    }

    /**
     * Calcula o código hash baseado nos atributos fone, email e foneContato.
     * 
     * @return o código hash da instância.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fone == null) ? 0 : fone.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((foneContato == null) ? 0 : foneContato.hashCode());
        return result;
    }

    /**
     * Compara se este objeto é igual a outro.
     * Dois objetos ContatoInfo são iguais se os seus atributos fone, email e
     * foneContato forem iguais.
     * 
     * @param obj o objeto a ser comparado.
     * @return true se forem iguais, false caso contrário.
     */
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

    /**
     * Retorna uma representação em String do objeto contendo os valores dos
     * atributos.
     * 
     * @return String com informações do ContatoInfo.
     */
    @Override
    public String toString() {
        return "ContatoInfo [fone=" + fone + ", email=" + email + ", foneContato=" + foneContato + "]";
    }

}
