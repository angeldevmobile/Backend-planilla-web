package com.example.SmartPayroll.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "descuentos")
public class Descuento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_descuento")
  private Integer idDescuento;

  private String nombre;
  private String tipo;
  private BigDecimal valor;
  private String descripcion;
  private Integer activo;

  // Getters y Setters
  public Integer getIdDescuento() {
    return idDescuento;
  }

  public void setIdDescuento(Integer idDescuento) {
    this.idDescuento = idDescuento;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getActivo() {
    return activo;
  }

  public void setActivo(Integer activo) {
    this.activo = activo;
  }
}
