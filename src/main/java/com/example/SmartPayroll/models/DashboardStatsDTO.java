package com.example.SmartPayroll.models;

public class DashboardStatsDTO {
    private String porcentajeAsistencia;
    private int vacaciones;
    private int ausencias;
    private int asistencias;

    // Constructor
    public DashboardStatsDTO(String porcentajeAsistencia, int vacaciones, int ausencias, int asistencias) {
        this.porcentajeAsistencia = porcentajeAsistencia;
        this.vacaciones = vacaciones;
        this.ausencias = ausencias;
        this.asistencias = asistencias;
    }

    // Getters y Setters
    public String getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(String porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public int getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(int vacaciones) {
        this.vacaciones = vacaciones;
    }

    public int getAusencias() {
        return ausencias;
    }

    public void setAusencias(int ausencias) {
        this.ausencias = ausencias;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }
}
