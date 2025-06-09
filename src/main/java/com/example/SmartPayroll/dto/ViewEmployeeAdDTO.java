package com.example.SmartPayroll.dto;

public class ViewEmployeeAdDTO {
    private Integer id_usuario;
    private String nombres;
    private String apellidos;
    private String correo;
    private String cargo;
    private String rol;
    private String estado;

    // Constructor completo
    public ViewEmployeeAdDTO(Integer id_usuario, String nombres, String apellidos, String correo, String cargo, String rol, String estado) {
        this.id_usuario = id_usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.cargo = cargo;
        this.rol = rol;
        this.estado = estado;
    }

    // Constructor para la consulta
    public ViewEmployeeAdDTO(String nombres, String apellidos, String correo, String cargo, String rol, String estado) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.cargo = cargo;
        this.rol = rol;
        this.estado = estado;
    }

    // Getters y setters
    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
