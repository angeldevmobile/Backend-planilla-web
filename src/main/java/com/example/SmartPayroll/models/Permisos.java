package com.example.SmartPayroll.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "permisos")
public class Permisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "tipo_permiso")
    private String tipoPermiso;

    @Column(name = "con_goce")
    private Boolean conGoce;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "documento_respaldo")
    private String documentoRespaldo;

    @Column(name = "observaciones")
    private String observaciones;

    // Getters y Setters
    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
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

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public Boolean getConGoce() {
        return conGoce;
    }

    public void setConGoce(Boolean conGoce) {
        this.conGoce = conGoce;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getDocumentoRespaldo() {
        return documentoRespaldo;
    }

    public void setDocumentoRespaldo(String documentoRespaldo) {
        this.documentoRespaldo = documentoRespaldo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
