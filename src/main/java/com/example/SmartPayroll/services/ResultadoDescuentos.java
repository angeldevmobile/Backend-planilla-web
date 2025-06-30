package com.example.SmartPayroll.services;

import com.example.SmartPayroll.models.Descuento;

import java.math.BigDecimal;
import java.util.List;

// DTO, no debe ser @Service
public class ResultadoDescuentos {
    private BigDecimal sueldoNeto;
    private List<Descuento> descuentosAplicados;
    private BigDecimal totalDescuentos;

    public ResultadoDescuentos(BigDecimal sueldoNeto, List<Descuento> descuentosAplicados, BigDecimal totalDescuentos) {
        this.sueldoNeto = sueldoNeto;
        this.descuentosAplicados = descuentosAplicados;
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getSueldoNeto() {
        return sueldoNeto;
    }

    public List<Descuento> getDescuentosAplicados() {
        return descuentosAplicados;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }
}
