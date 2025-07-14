package com.example.SmartPayroll.dto;

import java.time.LocalDate;

public class AusenciaConUsuarioDTO {
  private Long idAusencia;
  private LocalDate fecha;
  private String motivo;
  private Boolean justificada;
  private String observaciones;
  private String documentoRespaldo;
  private String estado;
  private String nombreUsuario;

  public AusenciaConUsuarioDTO(Long idAusencia, LocalDate fecha, String motivo, Boolean justificada,
      String observaciones, String documentoRespaldo, String estado, String nombreUsuario) {
    this.idAusencia = idAusencia;
    this.fecha = fecha;
    this.motivo = motivo;
    this.justificada = justificada;
    this.observaciones = observaciones;
    this.documentoRespaldo = documentoRespaldo;
    this.estado = estado;
    this.nombreUsuario = nombreUsuario;
  }

  // Getters
  public Long getIdAusencia() {
    return idAusencia;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public String getMotivo() {
    return motivo;
  }

  public Boolean getJustificada() {
    return justificada;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public String getDocumentoRespaldo() {
    return documentoRespaldo;
  }

  public String getEstado() {
    return estado;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }
}
