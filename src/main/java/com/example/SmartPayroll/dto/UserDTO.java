package com.example.SmartPayroll.dto;

public class UserDTO {
  private String nombres;
  private String apellidos;
  private String dni;
  private String telefono;
  private String correo;
  private String direccion;
  private String rol;
  private String idLogeo;

  public UserDTO(String nombres, String apellidos, String dni, String telefono,
      String correo, String direccion, String rol, String idLogeo) {
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.dni = dni;
    this.telefono = telefono;
    this.correo = correo;
    this.direccion = direccion;
    this.rol = rol;
    this.idLogeo = idLogeo;
  }

  // Getters
  public String getNombres() {
    return nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public String getDni() {
    return dni;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getCorreo() {
    return correo;
  }

  public String getDireccion() {
    return direccion;
  }

  public String getRol() {
    return rol;
  }

  public String getIdLogeo() {
    return idLogeo;
  }
}
