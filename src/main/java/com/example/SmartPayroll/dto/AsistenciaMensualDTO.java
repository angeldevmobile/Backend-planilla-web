package com.example.SmartPayroll.dto;

public class AsistenciaMensualDTO {
      private int mes;
    private int anio;
    private int totalAsistencias;

    public AsistenciaMensualDTO(int mes, int anio, int totalAsistencias) {
        this.mes = mes;
        this.anio = anio;
        this.totalAsistencias = totalAsistencias;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getTotalAsistencias() {
        return totalAsistencias;
    }

    public void setTotalAsistencias(int totalAsistencias) {
        this.totalAsistencias = totalAsistencias;
    }

    
}
