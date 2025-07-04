package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.projetofinalpoo.services.HashMD5Service;

/**
 * Representa um vigilante do sistema com suas informações de contrato e acesso.
 */
public class Vigilante implements Usuario {
    // Atributos
    private String login;
    private String senha;
    private String turno;
    private LocalTime cargaHoraria;
    private double remuneracao;
    private LocalDate dataContratacao;
    private String fone;
    private String email;
    private String foneContato;

    /**
     * Construtor da classe Vigilante.
     *
     * @param login            nome de usuário para login
     * @param senha            senha em texto plano (será criptografada)
     * @param turno            turno de trabalho (ex: manhã, tarde, noite)
     * @param cargaHoraria     carga horária diária no formato "HH:mm:ss"
     * @param remuneracao      valor da remuneração mensal
     * @param dataContratacao  data de contratação no formato "dd/MM/yyyy"
     * @param fone             telefone pessoal
     * @param email            e-mail institucional ou pessoal
     * @param foneContato      telefone para contato de emergência
     */
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

    /**
     * Retorna o login do vigilante.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do vigilante.
     *
     * @param login novo login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Retorna a senha criptografada do vigilante.
     *
     * @return senha criptografada
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do vigilante e aplica criptografia MD5.
     *
     * @param senha nova senha em texto plano
     */
    public void setSenha(String senha) {
        this.senha = HashMD5Service.gerarMD5(senha);
    }

    /**
     * Retorna o turno do vigilante.
     *
     * @return turno (Diurno, Noturno)
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Define o turno do vigilante.
     *
     * @param turno novo turno
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * Retorna a carga horária diária do vigilante.
     *
     * @return carga horária
     */
    public LocalTime getCargaHoraria() {
        return cargaHoraria;
    }

    /**
     * Define a carga horária diária do vigilante.
     *
     * @param cargaHoraria nova carga horária
     */
    public void setCargaHoraria(LocalTime cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * Retorna a remuneração mensal do vigilante.
     *
     * @return valor da remuneração
     */
    public double getRemuneracao() {
        return remuneracao;
    }

    /**
     * Define a remuneração mensal do vigilante.
     *
     * @param remuneracao novo valor da remuneração
     */
    public void setRemuneracao(double remuneracao) {
        this.remuneracao = remuneracao;
    }

    /**
     * Retorna a data de contratação do vigilante.
     *
     * @return data de contratação
     */
    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    /**
     * Define a data de contratação do vigilante.
     *
     * @param dataContratacao nova data de contratação
     */
    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    /**
     * Retorna o telefone pessoal do vigilante.
     *
     * @return número de telefone
     */
    public String getFone() {
        return fone;
    }

    /**
     * Define o telefone pessoal do vigilante.
     *
     * @param fone novo número de telefone
     */
    public void setFone(String fone) {
        this.fone = fone;
    }

    /**
     * Retorna o e-mail do vigilante.
     *
     * @return e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do vigilante.
     *
     * @param email novo e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o telefone de contato de emergência do vigilante.
     *
     * @return telefone de emergência
     */
    public String getFoneContato() {
        return foneContato;
    }

    /**
     * Define o telefone de contato de emergência do vigilante.
     *
     * @param foneContato novo telefone de emergência
     */
    public void setFoneContato(String foneContato) {
        this.foneContato = foneContato;
    }

    /**
     * Gera o código hash do objeto com base em seus atributos.
     *
     * @return valor do hash
     */
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

    /**
     * Verifica se dois vigilantes são iguais com base em seus atributos.
     *
     * @param obj objeto a ser comparado
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

    /**
     * Retorna uma representação textual do objeto Vigilante.
     *
     * @return string contendo os principais atributos do vigilante
     */
    @Override
    public String toString() {
        return "Vigilante [login=" + login + ", senha=" + senha + ", turno=" + turno + ", cargaHoraria=" + cargaHoraria
                + ", remuneracao=" + remuneracao + ", dataContratacao=" + dataContratacao + ", fone=" + fone
                + ", email=" + email + ", foneContato=" + foneContato + "]";
    }
}
