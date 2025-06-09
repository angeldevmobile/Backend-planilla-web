package com.example.SmartPayroll.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "tipos_contrato")
public class TipoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_contrato;

    private String nombre;

    // Getters y Setters

    public Integer getId_tipo_contrato() {
        return id_tipo_contrato;
    }

    public void setId_tipo_contrato(Integer id_tipo_contrato) {
        this.id_tipo_contrato = id_tipo_contrato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
