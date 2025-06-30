package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.ParametroLaboral;
import com.example.SmartPayroll.repositories.ParametroLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion")
public class ConfigurationController {

    @Autowired
    private ParametroLaboralRepository parametroLaboralRepository;

    @GetMapping("/parametros")
    public List<ParametroLaboral> getParametros() {
        return parametroLaboralRepository.findAll();
    }
}
