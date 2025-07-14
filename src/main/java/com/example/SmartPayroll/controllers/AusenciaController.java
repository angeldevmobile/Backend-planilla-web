package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.dto.AusenciaConUsuarioDTO;
import com.example.SmartPayroll.models.Ausencia;
import com.example.SmartPayroll.repositories.AusenciaRepository;
import com.example.SmartPayroll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ausencias")
public class AusenciaController {

  @Autowired
  private AusenciaRepository ausenciaRepository;

  @Autowired
  private UserRepository userRepository;

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

  @PutMapping("/{idAusencia}/estado")
  public ResponseEntity<?> actualizarEstadoAusencia(
      @PathVariable Long idAusencia,
      @RequestParam String estado // valores esperados: "aprobada" o "rechazada"
  ) {
      Optional<Ausencia> optional = ausenciaRepository.findById(idAusencia);
      if (optional.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("Ausencia no encontrada.");
      }
      Ausencia ausencia = optional.get();
      ausencia.setEstado(estado);
      ausenciaRepository.save(ausencia);
      return ResponseEntity.ok("Estado de ausencia actualizado a: " + estado);
  }

  @GetMapping("/pendientes")
  public List<Ausencia> obtenerAusenciasPendientes() {
      return ausenciaRepository.findByEstado("pendiente");
  }

  @GetMapping("/pendientes-con-nombre")
  public List<AusenciaConUsuarioDTO> obtenerAusenciasPendientesConNombre() {
      List<Ausencia> ausencias = ausenciaRepository.findByEstado("pendiente");
      List<AusenciaConUsuarioDTO> resultado = new ArrayList<>();
      for (Ausencia a : ausencias) {
          String nombreUsuario = userRepository.findById(a.getIdUsuario())
              .map(u -> u.getNombres() + " " + u.getApellidos())
              .orElse("Desconocido");
          resultado.add(new AusenciaConUsuarioDTO(
              a.getIdAusencia(),
              a.getFecha(),
              a.getMotivo(),
              a.getJustificada(),
              a.getObservaciones(),
              a.getDocumentoRespaldo(),
              a.getEstado(),
              nombreUsuario
          ));
      }
      return resultado;
  }
}