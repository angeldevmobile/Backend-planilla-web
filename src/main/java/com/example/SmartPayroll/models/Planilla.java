package com.example.SmartPayroll.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "planillas")
public class Planilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planilla")
    private Integer idPlanilla;

    @Column(name = "periodo_mes")
    @JsonProperty("periodo_mes")
    private Integer periodoMes;

    @Column(name = "periodo_anio")
    @JsonProperty("periodo_anio")
    private Integer periodoAnio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    @Column(name = "sueldo_bruto")
    private BigDecimal sueldoBruto;

    @Column(name = "bonificaciones")
    private BigDecimal bonificaciones;

    @Column(name = "total_descuentos")
    private BigDecimal totalDescuentos;

    @Column(name = "sueldo_neto")
    private BigDecimal sueldoNeto;

    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.DATE)
    private Date fechaGeneracion;

    @Column(name = "dias_asistidos")
    private Integer diasAsistidos;

    @Column(name = "dias_faltados")
    private Integer diasFaltados;

    @Column(name = "descuento_afp")
    private BigDecimal descuentoAfp;

    @Column(name = "descuento_onp")
    private BigDecimal descuentoOnp;

    // Getters y Setters con camelCase
    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Integer getPeriodoMes() {
        return periodoMes;
    }

    public void setPeriodoMes(Integer periodoMes) {
        this.periodoMes = periodoMes;
    }

    public Integer getPeriodoAnio() {
        return periodoAnio;
    }

    public void setPeriodoAnio(Integer periodoAnio) {
        this.periodoAnio = periodoAnio;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSueldoBruto() {
        return sueldoBruto;
    }

    public void setSueldoBruto(BigDecimal sueldoBruto) {
        this.sueldoBruto = sueldoBruto;
    }

    public BigDecimal getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(BigDecimal bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getSueldoNeto() {
        return sueldoNeto;
    }

    public void setSueldoNeto(BigDecimal sueldoNeto) {
        this.sueldoNeto = sueldoNeto;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Integer getDiasAsistidos() {
        return diasAsistidos;
    }

    public void setDiasAsistidos(Integer diasAsistidos) {
        this.diasAsistidos = diasAsistidos;
    }

    public Integer getDiasFaltados() {
        return diasFaltados;
    }

    public void setDiasFaltados(Integer diasFaltados) {
        this.diasFaltados = diasFaltados;
    }

    public BigDecimal getDescuentoAfp() {
        return descuentoAfp;
    }

    public void setDescuentoAfp(BigDecimal descuentoAfp) {
        this.descuentoAfp = descuentoAfp;
    }

    public BigDecimal getDescuentoOnp() {
        return descuentoOnp;
    }

    public void setDescuentoOnp(BigDecimal descuentoOnp) {
        this.descuentoOnp = descuentoOnp;
    }

    // Utilidad para repositorio
    public Integer getIdUsuario() {
        return usuario != null ? usuario.getIdUsuario() : null;
    }
}
