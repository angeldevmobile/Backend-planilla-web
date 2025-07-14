package com.example.SmartPayroll.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;



@Entity
@Table(name = "ausencias")
@Data
public class Ausencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ausencia")
    private Long idAusencia;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha")
    private LocalDate fecha;
    private String motivo;
    private Boolean justificada;
    private String observaciones;
    private String documentoRespaldo;
    @Column(name = "estado", nullable = false)
    private String estado = "pendiente";

    public Long getIdAusencia() {
        return idAusencia;
    }

    public void setIdAusencia(Long idAusencia) {
        this.idAusencia = idAusencia;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getJustificada() {
        return justificada;
    }

    public void setJustificada(Boolean justificada) {
        this.justificada = justificada;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



}