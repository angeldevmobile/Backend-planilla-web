package com.example.SmartPayroll.models;

import jakarta.persistence.*;

@Entity
@Table(name = "contrato_descuento")
public class ContratoDescuento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "id_tipo_contrato")
  private TipoContrato tipoContrato;

  @ManyToOne
  @JoinColumn(name = "id_descuento")
  private Descuento descuento;

  // Getters y Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TipoContrato getTipoContrato() {
    return tipoContrato;
  }

  public void setTipoContrato(TipoContrato tipoContrato) {
    this.tipoContrato = tipoContrato;
  }

  public Descuento getDescuento() {
    return descuento;
  }

  public void setDescuento(Descuento descuento) {
    this.descuento = descuento;
  }
}
