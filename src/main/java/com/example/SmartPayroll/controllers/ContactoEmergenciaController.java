package com.example.SmartPayroll.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.example.SmartPayroll.repositories.ContactoEmergenciaRepository;
import org.springframework.web.bind.annotation.RestController;
import com.example.SmartPayroll.models.ContactoEmergencia;

@RestController
@RequestMapping("/api/contacto-emergencia")
public class ContactoEmergenciaController {

    @Autowired
    private ContactoEmergenciaRepository repository;

    @GetMapping("/{idUsuario}")
    public List<ContactoEmergencia> getContactoPorUsuario(@PathVariable Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }
}
