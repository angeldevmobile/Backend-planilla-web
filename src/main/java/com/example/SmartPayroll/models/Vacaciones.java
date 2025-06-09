package com.example.SmartPayroll.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "vacaciones")
public class Vacaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacacion")
    private Integer idVacacion;

    @JsonProperty("id_usuario")
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @JsonProperty("fecha_inicio")
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @JsonProperty("fecha_fin")
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @JsonProperty("dias_calculados")
    @Column(name = "dias_calculados")
    private Integer diasCalculados;

    @JsonProperty("aprobado")
    @Column(name = "aprobado")
    private String aprobado;

    @JsonProperty("fecha_solicitud")
    @Column(name = "fecha_solicitud", nullable = false)
    private String fechaSolicitud;

    @JsonProperty("observaciones")
    @Column(name = "observaciones")
    private String observaciones;

    @JsonProperty("documento_respaldo")
    @Column(name = "documento_respaldo")
    private String documentoRespaldo;

    // Getters y Setters
    public Integer getIdVacacion() {
        return idVacacion;
    }

    public void setIdVacacion(Integer idVacacion) {
        this.idVacacion = idVacacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiasCalculados() {
        return diasCalculados;
    }

    public void setDiasCalculados(Integer diasCalculados) {
        this.diasCalculados = diasCalculados;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDocumentoRespaldo() {
        return documentoRespaldo;
    }

    public void setDocumentoRespaldo(String documentoRespaldo) {
        this.documentoRespaldo = documentoRespaldo;
    }
}