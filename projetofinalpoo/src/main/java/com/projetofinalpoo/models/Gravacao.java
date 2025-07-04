package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Gravacao {
    //Atributos
    private int id;
    private LocalDate data;
    private LocalTime duracao;
    private String arquivo;
    private String descricao;
    private int idProduto;

    //Construtor
    public Gravacao(int id, String data, String duracao, String arquivo, String descricao, int idProduto) {
        this.id = id;
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.arquivo = arquivo;
        this.descricao = descricao;
        this.idProduto = idProduto;
    }

    //Metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
        result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + idProduto;
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
        Gravacao other = (Gravacao) obj;
        if (id != other.id)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (duracao == null) {
            if (other.duracao != null)
                return false;
        } else if (!duracao.equals(other.duracao))
            return false;
        if (arquivo == null) {
            if (other.arquivo != null)
                return false;
        } else if (!arquivo.equals(other.arquivo))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (idProduto != other.idProduto)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Gravacao [id=" + id + ", data=" + data + ", duracao=" + duracao + ", arquivo=" + arquivo
                + ", descricao=" + descricao + ", idProduto=" + idProduto + "]";
    }
    
}
