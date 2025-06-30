package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Ausencia;
import com.example.SmartPayroll.repositories.AusenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/ausencias")
public class AusenciaController {

  @Autowired
  private AusenciaRepository ausenciaRepository;

  @GetMapping("/no-justificadas/{id_usuario}")
  public ResponseEntity<List<Ausencia>> obtenerAusenciasNoJustificadas(@PathVariable int id_usuario) {
    List<Ausencia> ausencias = ausenciaRepository.findByIdUsuarioAndJustificadaFalse(id_usuario);
    return ResponseEntity.ok(ausencias);
  }

  @PutMapping("/{idUsuario}/justificar")
  public ResponseEntity<?> justificarAusenciaPorUsuarioYFecha(
      @PathVariable int idUsuario,
      @RequestBody Ausencia datosJustificacion) {
    Optional<Ausencia> optional = ausenciaRepository.findByIdUsuarioAndFecha(
        idUsuario, 
        datosJustificacion.getFecha()
    );

    if (optional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("Ausencia no encontrada para el usuario y fecha indicada.");
    }

    Ausencia ausencia = optional.get();
    ausencia.setMotivo(datosJustificacion.getMotivo());
    ausencia.setObservaciones(datosJustificacion.getObservaciones());
    ausencia.setJustificada(true);

    if (datosJustificacion.getDocumentoRespaldo() != null) {
      ausencia.setDocumentoRespaldo(datosJustificacion.getDocumentoRespaldo());
    }

    ausenciaRepository.save(ausencia);
    return ResponseEntity.ok("Ausencia justificada correctamente.");
  }

  @GetMapping("/usuario/{idUsuario}")
  public List<Ausencia> obtenerAusenciasPorUsuario(@PathVariable int idUsuario) {
    return ausenciaRepository.findByIdUsuario(idUsuario);
  }
}