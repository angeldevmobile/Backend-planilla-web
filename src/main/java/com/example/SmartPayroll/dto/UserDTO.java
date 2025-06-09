package com.example.SmartPayroll.dto;


public class UserDTO {
  private int id_usuario;

  private String nombres;
  private String apellidos;
  private String dni;
  private String telefono;
  private String correo;
  private String direccion;
  private String rol;
  private String idLogeo;
  private String cargo;
  private String fecha_ingreso;
  private String fecha_nacimiento;

  public UserDTO(int id_usuario, String nombres, String apellidos, String dni, String telefono, String correo,
      String direccion,
      String rol, String idLogeo, String cargo, String fecha_ingreso, String fecha_nacimiento) {
    this.id_usuario = id_usuario;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.dni = dni;
    this.telefono = telefono;
    this.correo = correo;
    this.direccion = direccion;
    this.rol = rol;
    this.idLogeo = idLogeo;
    this.cargo = cargo;
    this.fecha_ingreso = fecha_ingreso;
    this.fecha_nacimiento = fecha_nacimiento;
  }

  public int getId_usuario() {
    return id_usuario;
  }

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

  public String getCargo() {
    return cargo;
  }

  public String getFecha_ingreso() {
    return fecha_ingreso;
  }

  public String getFecha_nacimiento() {
    return fecha_nacimiento;
  }
}
