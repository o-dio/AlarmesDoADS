package com.projetofinalpoo.models;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.projetofinalpoo.services.HashMD5Service;

public class Vigilante implements Usuario{
    //Atributos
    private String login;
    private String senha;
    private String turno;
    private LocalTime cargaHoraria;
    private double remuneracao;
    private LocalDate dataContratacao;
    private String fone;
    private String email;
    private String foneContato;

    //Construtor
    public Vigilante(String login, String senha, String turno, String cargaHoraria, double remuneracao,
                     String dataContratacao, String fone, String email, String foneContato) {
        this.login = login;
        this.senha = senha;
        this.turno = turno;
        this.cargaHoraria = LocalTime.parse(cargaHoraria, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.remuneracao = remuneracao;
        this.dataContratacao = LocalDate.parse(dataContratacao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.fone = fone;
        this.email = email;
        this.foneContato = foneContato;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public LocalTime getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(LocalTime cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public double getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(double remuneracao) {
        this.remuneracao = remuneracao;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

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
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((turno == null) ? 0 : turno.hashCode());
        result = prime * result + ((cargaHoraria == null) ? 0 : cargaHoraria.hashCode());
        long temp;
        temp = Double.doubleToLongBits(remuneracao);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((dataContratacao == null) ? 0 : dataContratacao.hashCode());
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
        Vigilante other = (Vigilante) obj;
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
        if (turno == null) {
            if (other.turno != null)
                return false;
        } else if (!turno.equals(other.turno))
            return false;
        if (cargaHoraria == null) {
            if (other.cargaHoraria != null)
                return false;
        } else if (!cargaHoraria.equals(other.cargaHoraria))
            return false;
        if (Double.doubleToLongBits(remuneracao) != Double.doubleToLongBits(other.remuneracao))
            return false;
        if (dataContratacao == null) {
            if (other.dataContratacao != null)
                return false;
        } else if (!dataContratacao.equals(other.dataContratacao))
            return false;
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
        return "Vigilante [login=" + login + ", senha=" + senha + ", turno=" + turno + ", cargaHoraria=" + cargaHoraria
                + ", remuneracao=" + remuneracao + ", dataContratacao=" + dataContratacao + ", fone=" + fone
                + ", email=" + email + ", foneContato=" + foneContato + "]";
    }
}
