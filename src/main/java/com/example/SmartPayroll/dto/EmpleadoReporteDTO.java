
package com.example.SmartPayroll.dto;

public class EmpleadoReporteDTO {
    private Long id;
    private String nombreCompleto;

    public EmpleadoReporteDTO(Long id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}