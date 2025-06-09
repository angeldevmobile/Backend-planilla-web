package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Permisos;
import com.example.SmartPayroll.repositories.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    @Autowired
    private PermisoRepository permisoRepository;

    @GetMapping
    public List<Permisos> getAllPermisos() {
        return permisoRepository.findAll();
    }
}
