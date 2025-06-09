package com.example.SmartPayroll.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.SmartPayroll.dto.ViewEmployeeAdDTO;
import com.example.SmartPayroll.repositories.ViewEmployeeAdRepository;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class ViewEmpleadoAdController {

    @Autowired
    private ViewEmployeeAdRepository empleadoService;

    @GetMapping
    public List<ViewEmployeeAdDTO> listarEmpleados() {
        return empleadoService.getAllEmpleados();
    }
}