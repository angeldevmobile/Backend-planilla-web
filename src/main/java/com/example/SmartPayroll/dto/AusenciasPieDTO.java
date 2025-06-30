package com.example.SmartPayroll.dto;

public class AusenciasPieDTO {
    private int justificadas;
    private int noJustificadas;

    public AusenciasPieDTO(int justificadas, int noJustificadas) {
        this.justificadas = justificadas;
        this.noJustificadas = noJustificadas;
    }

    public int getJustificadas() { return justificadas; }
    public int getNoJustificadas() { return noJustificadas; }
}
