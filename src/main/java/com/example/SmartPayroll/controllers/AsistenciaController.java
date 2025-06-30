package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Asistencia;
import com.example.SmartPayroll.repositories.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

  @Autowired
  private AsistenciaRepository asistenciaRepository;

  // Registrar nueva asistencia
  @PostMapping
  public ResponseEntity<Asistencia> registrarAsistencia(@RequestBody Asistencia asistencia) {
    asistencia.setHoraSalida(null); // ingreso aún no tiene salida
    Asistencia saved = asistenciaRepository.save(asistencia);
    return ResponseEntity.ok(saved);
  }

  // Consultar asistencia del día por usuario
  @GetMapping("/{id_usuario}")
  public ResponseEntity<?> obtenerAsistenciaDeHoy(@PathVariable int id_usuario, @RequestParam String fecha) {
    try {
      LocalDate fechaAsDate = LocalDate.parse(fecha); 
      Optional<Asistencia> asistencia = asistenciaRepository.findByIdUsuarioAndFecha(id_usuario, fechaAsDate);
      if (asistencia.isPresent()) {
        return ResponseEntity.ok(asistencia.get());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("No se encontró asistencia para el usuario y fecha indicada.");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha inválido. Usa yyyy-MM-dd.");
    }
  }

  // Actualizar hora de salida
  @PutMapping("/salida/{id_usuario}")
  public ResponseEntity<?> actualizarHoraSalida(
      @PathVariable int id_usuario,
      @RequestParam String horaSalida,
      @RequestParam String fecha) {

    try {
      LocalDate fechaAsDate = LocalDate.parse(fecha); // Convertir String a LocalDate
      Optional<Asistencia> optionalAsistencia = asistenciaRepository.findByIdUsuarioAndFecha(id_usuario, fechaAsDate);

      if (optionalAsistencia.isPresent()) {
        Asistencia asistencia = optionalAsistencia.get();
        asistencia.setHoraSalida(horaSalida);
        asistenciaRepository.save(asistencia);
        return ResponseEntity.ok("Hora de salida actualizada correctamente");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró asistencia para esa fecha");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha inválido. Usa yyyy-MM-dd.");
    }
  }

  // Obtener todas las asistencias de un usuario
  @GetMapping("/todos/{id_usuario}")
  public ResponseEntity<?> obtenerAsistenciasPorUsuario(@PathVariable int id_usuario) {
    List<Asistencia> asistencias = asistenciaRepository.findAllByIdUsuario(id_usuario);
    return ResponseEntity.ok(asistencias);
  }
}
