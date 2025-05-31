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
    private Integer id_usuario;
    
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
    private int estado;
}