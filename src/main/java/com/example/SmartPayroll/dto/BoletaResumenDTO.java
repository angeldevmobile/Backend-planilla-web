package com.example.SmartPayroll.dto;

public class BoletaResumenDTO {
     private int mes;
    private int anio;
    private String rutaArchivo;

    // Constructor
    public BoletaResumenDTO(int mes, int anio, String rutaArchivo) {
        this.mes = mes;
        this.anio = anio;
        this.rutaArchivo = rutaArchivo;
    }

    // Getters
    public int getMes() { return mes; }
    public int getAnio() { return anio; }
    public String getRutaArchivo() { return rutaArchivo; }
}
