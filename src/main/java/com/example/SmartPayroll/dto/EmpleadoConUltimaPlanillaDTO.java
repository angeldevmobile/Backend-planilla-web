package com.example.SmartPayroll.dto;

public class EmpleadoConUltimaPlanillaDTO {
  private Integer id_usuario;
  private String nombres;
  private String apellidos;
  private String correo;
  private String cargo;
  private String rol;
  private String estado;
  private Integer idPlanilla;

  // Getters y setters
  public Integer getId_usuario() {
    return id_usuario;
  }

  public void setId_usuario(Integer id_usuario) {
    this.id_usuario = id_usuario;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Integer getIdPlanilla() {
    return idPlanilla;
  }

  public void setIdPlanilla(Integer idPlanilla) {
    this.idPlanilla = idPlanilla;
  }
}
