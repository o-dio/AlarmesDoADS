package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Representa um vigilante do sistema com suas informações de contrato e acesso.
 */
public class Vigilante implements Usuario {
    // Atributos
    private String login;
    private String senha;
    private String turno;

     /** Carga horaria de vigilante no formato HH:mm:ss para JSON. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime cargaHoraria;

    private Double remuneracao;

    /** Data da contratacao de vigilante no formato HH:mm:ss para JSON. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataContratacao;

    private ContatoInfo contatoInfo;

    /**
     * Construtor da classe Vigilante.
     *
     * @param login           nome de usuário para login
     * @param senha           senha em texto plano (será criptografada)
     * @param turno           turno de trabalho (ex: manhã, tarde, noite)
     * @param cargaHoraria    carga horária diária no formato "HH:mm:ss"
     * @param remuneracao     valor da remuneração mensal
     * @param dataContratacao data de contratação no formato "dd/MM/yyyy"
     * @param contatoInfo     informações para contatar o vigilante
     */
    public Vigilante(String login, String senha, String turno, String cargaHoraria, Double remuneracao,
            String dataContratacao, ContatoInfo contatoInfo) {
        this.login = login;
        this.senha = senha;
        this.turno = turno;
        this.cargaHoraria = LocalTime.parse(cargaHoraria, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.remuneracao = remuneracao;
        this.dataContratacao = LocalDate.parse(dataContratacao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.contatoInfo = contatoInfo;
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
     * Define a senha do vigilante.
     *
     * @param senha nova senha em texto plano
     */
    public void setSenha(String senha) {
        this.senha = senha;
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
    public Double getRemuneracao() {
        return remuneracao;
    }

    /**
     * Define a remuneração mensal do vigilante.
     *
     * @param remuneracao novo valor da remuneração
     */
    public void setRemuneracao(Double remuneracao) {
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
     * Retorna um objeto ContatoInfo com as informações de contato de um vigilante
     * 
     * @return Objeto ContatoInfo
     */
    public ContatoInfo getContatoInfo() {
        return contatoInfo;
    }

    /**
     * Define as informações para contato de um vigilante
     * 
     * @param contatoInfo informações de contato
     */
    public void setContatoInfo(ContatoInfo contatoInfo) {
        this.contatoInfo = contatoInfo;
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
        result = prime * result + ((contatoInfo == null) ? 0 : contatoInfo.hashCode());
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
        if (contatoInfo == null) {
            if (other.contatoInfo != null)
                return false;
        } else if (!contatoInfo.equals(other.contatoInfo))
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
                + ", remuneracao=" + remuneracao + ", dataContratacao=" + dataContratacao + ", contatoInfo="
                + contatoInfo + "]";
    }

}
