package com.example.SmartPayroll.dto;

public class BoletaDTO {
  private Long id_boleta;
  private String nombre_mes;
  private int periodo_anio;
  private String fecha_generacion;
  private String ruta_archivo;

  public BoletaDTO(Long id_boleta, String nombre_mes, int periodo_anio, String fecha_generacion, String ruta_archivo) {
    this.id_boleta = id_boleta;
    this.nombre_mes = nombre_mes;
    this.periodo_anio = periodo_anio;
    this.fecha_generacion = fecha_generacion; 
    this.ruta_archivo = ruta_archivo;
  }

  // Getters y setters
  public Long getId_boleta() {
    return id_boleta;
  }

  public void setId_boleta(Long id_boleta) {
    this.id_boleta = id_boleta;
  }

  public String getNombre_mes() {
    return nombre_mes;
  }

  public void setNombre_mes(String nombre_mes) {
    this.nombre_mes = nombre_mes;
  }

  public int getPeriodo_anio() {
    return periodo_anio;
  }

  public void setPeriodo_anio(int periodo_anio) {
    this.periodo_anio = periodo_anio;
  }

  public String getFecha_generacion() {
    return fecha_generacion;
  }

  public void setFecha_generacion(String fecha_generacion) {
    this.fecha_generacion = fecha_generacion;
  }

  public String getRuta_archivo() {
    return ruta_archivo;
  }

  public void setRuta_archivo(String ruta_archivo) {
    this.ruta_archivo = ruta_archivo;
  }
}