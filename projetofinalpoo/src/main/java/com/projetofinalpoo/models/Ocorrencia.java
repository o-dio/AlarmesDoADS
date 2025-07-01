package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ocorrencia {
    //Atributos
    private LocalDate data;
    private LocalTime duracao;
    private int idVigilante;
    private int idProduto;

    //Construtor
    public Ocorrencia(String data, String duracao, int idVigilante, int idProduto) {
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.duracao = LocalTime.parse(duracao, DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.idVigilante = idVigilante;
        this.idProduto = idProduto;
    }

    //Metodos
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

    public int getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
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
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
        result = prime * result + idVigilante;
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
        Ocorrencia other = (Ocorrencia) obj;
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
        if (idVigilante != other.idVigilante)
            return false;
        if (idProduto != other.idProduto)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ocorrencia [data=" + data + ", duracao=" + duracao +
               ", idVigilante=" + idVigilante + ", idProduto=" + idProduto + "]";
    }
}
