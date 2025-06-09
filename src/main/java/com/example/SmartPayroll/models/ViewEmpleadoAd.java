package com.example.SmartPayroll.models;

import com.example.SmartPayroll.dto.ViewEmployeeAdDTO;
import com.example.SmartPayroll.repositories.ViewEmployeeAdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewEmpleadoAd {

    @Autowired
    private ViewEmployeeAdRepository empleadoRepository;

    public List<ViewEmployeeAdDTO> getAllEmpleados() {
        List<User> usuarios = empleadoRepository.findAll();
        System.out.println("Usuarios recuperados: " + usuarios); 
        return usuarios.stream().map(user -> new ViewEmployeeAdDTO(
                user.getId_usuario(),
                user.getNombres(),
                user.getApellidos(),
                user.getCorreo(),
                user.getCargo(),
                user.getRol(),
                user.getEstado() 
        )).collect(Collectors.toList());
    }
}