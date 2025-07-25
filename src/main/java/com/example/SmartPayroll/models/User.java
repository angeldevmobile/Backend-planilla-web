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
}
