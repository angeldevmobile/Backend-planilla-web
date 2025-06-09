package com.example.SmartPayroll.models;

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
    private Integer id_planilla;

    private Integer periodo_mes;
    private Integer periodo_anio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    private BigDecimal sueldoBruto;
    private BigDecimal bonificaciones;
    private BigDecimal totalDescuentos;
    private BigDecimal sueldoNeto;

    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.DATE)
    private Date fechaGeneracion;

    public Integer getId_planilla() {
        return id_planilla;
    }

    public void setId_planilla(Integer id_planilla) {
        this.id_planilla = id_planilla;
    }

    public Integer getPeriodo_mes() {
        return periodo_mes;
    }

    public void setPeriodo_mes(Integer periodo_mes) {
        this.periodo_mes = periodo_mes;
    }

    public Integer getPeriodo_anio() {
        return periodo_anio;
    }

    public void setPeriodo_anio(Integer periodo_anio) {
        this.periodo_anio = periodo_anio;
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

    public Integer getIdUsuario() {
        return usuario != null ? usuario.getId_usuario() : null;
    }

    public void setUsuarioId(Integer idUsuario) {
        if (this.usuario == null) {
            this.usuario = new User();
        }
        this.usuario.setId_usuario(idUsuario);
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
