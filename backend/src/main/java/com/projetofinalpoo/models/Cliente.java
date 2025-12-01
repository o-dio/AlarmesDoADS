package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.projetofinalpoo.services.HashMD5Service;
import com.projetofinalpoo.models.Contrato;

/**
 * Representa um cliente do sistema.
 * Implementa a interface {@code Usuario} e armazena informações pessoais e de contato.
 * Um cliente pode possuir múltiplos {@link Contrato}.
 */
public class Cliente implements Usuario {

    // Atributos
    private String login;
    private String senha;
    private String cpf;
    private LocalDate dataNasc;
    private ContatoInfo contatoInfo;

    // Lista de contratos do cliente
    private List<Contrato> contratos = new ArrayList<>();

    /**
     * Construtor padrão.
     */
    public Cliente() {
    }

    /**
     * Construtor completo que inicializa o cliente com login, senha, CPF, data de nascimento e informações de contato.
     *
     * @param login       Login do cliente.
     * @param senha       Senha do cliente (será armazenada como hash MD5).
     * @param cpf         CPF do cliente.
     * @param dataNasc    Data de nascimento do cliente no formato "dd/MM/yyyy".
     * @param contatoInfo Informações de contato do cliente.
     */
    public Cliente(String login, String senha, String cpf, String dataNasc, ContatoInfo contatoInfo) {
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNasc = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.contatoInfo = contatoInfo;
    }

    /**
     * Adiciona um contrato ao cliente, garantindo que a relação bidirecional seja atualizada.
     *
     * @param contrato Contrato a ser adicionado.
     */
    public void adicionarContrato(Contrato contrato) {
        if (!contratos.contains(contrato)) {
            contratos.add(contrato);
            contrato.setCliente(this);
        }
    }

    /**
     * Remove um contrato do cliente, garantindo que a relação bidirecional seja atualizada.
     *
     * @param contrato Contrato a ser removido.
     */
    public void removerContrato(Contrato contrato) {
        if (contratos.contains(contrato)) {
            contratos.remove(contrato);
            if (contrato.getCliente() == this) {
                contrato.setCliente(null);
            }
        }
    }

    /**
     * Retorna todos os contratos do cliente.
     *
     * @return Lista de contratos associados ao cliente.
     */
    public List<Contrato> getContratos() {
        return contratos;
    }

    // Getters e Setters com JavaDoc

    /** @return Login do cliente. */
    public String getLogin() {
        return login;
    }

    /** @param login Define o login do cliente. */
    public void setLogin(String login) {
        this.login = login;
    }

    /** @return Senha do cliente (hash MD5). */
    public String getSenha() {
        return senha;
    }

    /** 
     * Define a senha do cliente, armazenando-a como hash MD5.
     * 
     * @param senha Senha em texto puro.
     */
    public void setSenha(String senha) {
        this.senha = HashMD5Service.gerarMD5(senha);
    }

    /** @return CPF do cliente. */
    public String getCpf() {
        return cpf;
    }

    /** @param cpf Define o CPF do cliente. */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /** @return Data de nascimento do cliente. */
    public LocalDate getDataNasc() {
        return dataNasc;
    }

    /** @param dataNasc Define a data de nascimento do cliente. */
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    /** @return Informações de contato do cliente. */
    public ContatoInfo getContatoInfo() {
        return contatoInfo;
    }

    /** @param contatoInfo Define as informações de contato do cliente. */
    public void setContatoInfo(ContatoInfo contatoInfo) {
        this.contatoInfo = contatoInfo;
    }

    /**
     * Retorna o código hash do cliente, baseado no login e CPF.
     *
     * @return Código hash do cliente.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        return result;
    }

    /**
     * Compara este cliente com outro objeto para verificar igualdade.
     * Dois clientes são considerados iguais se tiverem o mesmo login e CPF.
     *
     * @param obj Objeto a ser comparado.
     * @return {@code true} se os clientes forem iguais; {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Cliente other = (Cliente) obj;

        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;

        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;

        return true;
    }

    /**
     * Retorna uma representação em String do cliente, incluindo login, CPF, data de nascimento,
     * informações de contato e quantidade de contratos associados.
     *
     * @return Representação em String do cliente.
     */
    @Override
    public String toString() {
        return "Cliente [login=" + login + ", senha=" + senha + ", cpf=" + cpf +
            ", dataNasc=" + dataNasc + ", contatoInfo=" + contatoInfo +
            ", contratos=" + contratos.size() + "]";
    }
}
