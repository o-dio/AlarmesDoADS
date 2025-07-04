package com.projetofinalpoo.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Trajeto {
    //Atributos
    private int id;
    private LocalDate dataIni;
    private LocalDate dataFim;
    private int idVigilante;
    private int idRota;
 
    // Construtor 
    public Trajeto() {}

    public Trajeto(String dataIni, String dataFim, int idVigilante, int idRota) {
        this.dataIni = LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dataFim != null) {
            this.dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.dataFim = null;
        }
        this.idVigilante = idVigilante;
        this.idRota = idRota;
    }

    public Trajeto(int id, String dataIni, String dataFim, int idVigilante, int idRota) {
        this.id = id;
        this.dataIni = LocalDate.parse(dataIni, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dataFim != null) {
            this.dataFim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.dataFim = null;
        }
        this.idVigilante = idVigilante;
        this.idRota = idRota;
    }
    
    //Metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataIni() {
        return dataIni;
    }

    public void setDataIni(LocalDate dataIni) {
        this.dataIni = dataIni;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
    }

    public int getIdRota() {
        return idRota;
    }

    public void setIdRota(int idRota) {
        this.idRota = idRota;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataIni == null) ? 0 : dataIni.hashCode());
        result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
        result = prime * result + idVigilante;
        result = prime * result + idRota;
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
        Trajeto other = (Trajeto) obj;
        if (dataIni == null) {
            if (other.dataIni != null)
                return false;
        } else if (!dataIni.equals(other.dataIni))
            return false;
        if (dataFim == null) {
            if (other.dataFim != null)
                return false;
        } else if (!dataFim.equals(other.dataFim))
            return false;
        if (idVigilante != other.idVigilante)
            return false;
        if (idRota != other.idRota)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Trajeto [dataIni=" + dataIni + ", dataFim=" + dataFim + ", idVigilante=" + idVigilante + ", idRota=" + idRota + "]";
    }
}
