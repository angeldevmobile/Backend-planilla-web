package com.example.SmartPayroll.dto;

import java.math.BigDecimal;

public class AdRegisterEmployeeDTO {
  // Datos de usuario registrado

  public String nombres;
  public String apellidos;
  public String dni;
  public String telefono;
  public String correo;
  public String direccion;
  public String rol;
  public String cargo;
  public String estado;
  public String sexo;
  public String fecha_nacimiento;
  public String turno;
  public String fechaIngreso;

  //contrato

  public Integer idTipoContrato;
  public String fechaInicioContrato;
  public String fechaFinContrato;
  public BigDecimal sueldoBruto;
  public String condicionesContrato;

  //Planilla
  public Integer periodoMes;
  public Integer periodoAnio;
  public BigDecimal bonificaciones;

  //Contacto de emergencia
  public String nombreContacto;
  public String telefonoContacto;
  public String parentescoContacto;
  public String direccionContacto;
}
