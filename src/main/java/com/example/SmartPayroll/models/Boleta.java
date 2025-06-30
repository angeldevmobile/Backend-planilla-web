package com.example.SmartPayroll.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalDate;



@Entity
@Table(name = "boletas")

public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_boleta;

    private String ruta_archivo;
    
    @Column(name = "fecha_generacion")
    private LocalDate fecha_generacion;

    

    public Long getId_boleta() {
        return id_boleta;
    }



    public void setId_boleta(Long id_boleta) {
        this.id_boleta = id_boleta;
    }



    public String getRuta_archivo() {
        return ruta_archivo;
    }



    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }



    public LocalDate getFecha_generacion() {
        return fecha_generacion;
    }



    public void setFecha_generacion(LocalDate fecha_generacion) {
        this.fecha_generacion = fecha_generacion;
    }



    public Planilla getPlanilla() {
        return planilla;
    }



    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }



    @ManyToOne
    @JoinColumn(name = "id_planilla")
    private Planilla planilla;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
