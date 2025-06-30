package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Vacaciones;
import com.example.SmartPayroll.repositories.VacacionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacaciones")
public class VacacionesController {

  @Autowired
  private VacacionRepository vacacionRepository;

  @PostMapping("/registrar")
  public ResponseEntity<?> registrarVacaciones(@RequestBody Vacaciones vacacion) {
    try {
      Vacaciones nuevaVacacion = vacacionRepository.save(vacacion);
      return ResponseEntity.ok(nuevaVacacion);
    } catch (Exception e) {
      e.printStackTrace(); // Esto muestra el error en la consola
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error al registrar vacaciones: " + e.getMessage());
    }
  }

  // obtener vacaciones por usuario
  @GetMapping("/usuario/{idUsuario}")
  public List<Vacaciones> obtenerPorUsuario(@PathVariable int idUsuario) {
    return vacacionRepository.findByIdUsuario(idUsuario);
  }
}