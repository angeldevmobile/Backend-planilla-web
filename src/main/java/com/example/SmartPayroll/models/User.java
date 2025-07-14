package com.example.SmartPayroll.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") 
    private Integer idUsuario;

    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String correo;
    private String direccion;

    @Column(unique = true)
    private String idLogeo;

    private String password;
    private String rol;
    private String estado;

    private String cargo;
    private String fecha_ingreso;
    private String fecha_nacimiento;

    // Campos adicionales
    private String sexo;
    private String turno;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("idUsuario=").append(idUsuario);
        sb.append(", nombres=").append(nombres);
        sb.append(", apellidos=").append(apellidos);
        sb.append(", dni=").append(dni);
        sb.append(", telefono=").append(telefono);
        sb.append(", correo=").append(correo);
        sb.append(", direccion=").append(direccion);
        sb.append(", idLogeo=").append(idLogeo);
        sb.append(", password=").append(password);
        sb.append(", rol=").append(rol);
        sb.append(", estado=").append(estado);
        sb.append(", cargo=").append(cargo);
        sb.append(", fecha_ingreso=").append(fecha_ingreso);
        sb.append(", fecha_nacimiento=").append(fecha_nacimiento);
        sb.append(", sexo=").append(sexo);
        sb.append(", turno=").append(turno);
        sb.append('}');
        return sb.toString();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdLogeo() {
        return idLogeo;
    }

    public void setIdLogeo(String idLogeo) {
        this.idLogeo = idLogeo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }


    
}
