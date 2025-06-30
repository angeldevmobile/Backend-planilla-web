package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Permisos;
import com.example.SmartPayroll.repositories.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocumento(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo vacío");
        }
        return ResponseEntity.ok(file.getOriginalFilename());
    }

    @PostMapping
    public Permisos crearPermiso(@RequestBody Permisos permiso) {
        return permisoRepository.save(permiso);
    }
}
