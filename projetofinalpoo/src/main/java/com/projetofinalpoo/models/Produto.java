package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Produto {
    //Atributos
    private int id;
    private LocalDate dataInst;
    private LocalDate dataRet;
    private boolean defeito;
    private int idEndereco;

    //Construtor
    public Produto(){}
    
    public Produto(int id, String dataInst, String dataRet, boolean defeito, int idEndereco) {
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataInst = (dataInst != null && !dataInst.isEmpty()) ? LocalDate.parse(dataInst, formatter) : null;
        this.dataRet = (dataRet != null && !dataRet.isEmpty()) ? LocalDate.parse(dataRet, formatter) : null;
        this.defeito = defeito;
        this.idEndereco = idEndereco;
    }

    //Metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInst() {
        return dataInst;
    }

    public void setDataInst(LocalDate dataInst) {
        this.dataInst = dataInst;
    }

    public LocalDate getDataRet() {
        return dataRet;
    }

    public void setDataRet(LocalDate dataRet) {
        this.dataRet = dataRet;
    }

    public boolean isDefeito() {
        return defeito;
    }

    public void setDefeito(boolean defeito) {
        this.defeito = defeito;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((dataInst == null) ? 0 : dataInst.hashCode());
        result = prime * result + ((dataRet == null) ? 0 : dataRet.hashCode());
        result = prime * result + (defeito ? 1231 : 1237);
        result = prime * result + idEndereco;
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
        Produto other = (Produto) obj;
        if (id != other.id)
            return false;
        if (dataInst == null) {
            if (other.dataInst != null)
                return false;
        } else if (!dataInst.equals(other.dataInst))
            return false;
        if (dataRet == null) {
            if (other.dataRet != null)
                return false;
        } else if (!dataRet.equals(other.dataRet))
            return false;
        if (defeito != other.defeito)
            return false;
        if (idEndereco != other.idEndereco)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", dataInst=" + dataInst + ", dataRet=" + dataRet + ", defeito=" + defeito
                + ", idEndereco=" + idEndereco + "]";
    }
    
}
