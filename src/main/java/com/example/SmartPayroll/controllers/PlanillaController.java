package com.example.SmartPayroll.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartPayroll.dto.EmpleadoConUltimaPlanillaDTO;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.models.Planilla;
import com.example.SmartPayroll.models.Boleta;
import com.example.SmartPayroll.repositories.PlanillaRepository;
import com.example.SmartPayroll.repositories.UserRepository;
import com.example.SmartPayroll.repositories.BoletaRepository;
import com.example.SmartPayroll.services.PlanillaService;

// Agrega esta importación:
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/planillas")
public class PlanillaController {

  // Agrega el logger:
  private static final Logger logger = LoggerFactory.getLogger(PlanillaController.class);

  @Autowired
  private PlanillaService planillaService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlanillaRepository planillaRepository;

  @Autowired
  private BoletaRepository boletaRepository;

  // Genera y guarda la planilla para un usuario específico
  @PostMapping("/generar/{idUsuario}")
  public String generarPlanillaUsuario(@PathVariable Integer idUsuario, @RequestBody Planilla planilla) {
    User usuario = userRepository.findById(idUsuario).orElse(null);
    if (usuario == null) {
        return "Usuario no encontrado";
    }
    planilla.setUsuario(usuario);

    // Setea el periodo antes de guardar
    planilla.setPeriodoMes(LocalDate.now().getMonthValue());
    planilla.setPeriodoAnio(LocalDate.now().getYear());

    Planilla nuevaPlanilla = planillaService.procesarYGuardarPlanilla(planilla);

    // Aquí va el bloque:
    Boleta boleta = new Boleta();
    boleta.setPlanilla(nuevaPlanilla);
    boleta.setUsuario(usuario); 
    boleta.setFecha_generacion(LocalDate.now());
    boleta.setRuta_archivo("https://backend-planilla-web.onrender.com/api/planillas/boleta/" + nuevaPlanilla.getIdPlanilla() + "/pdf");
    boletaRepository.save(boleta);

    return "Planilla y boleta generadas para el usuario " + idUsuario;
  }

  // Nuevo endpoint para el total de nómina mensual
  @GetMapping("/total-mensual")
  public BigDecimal getTotalNominaMensual() {
    return planillaService.getTotalSueldoBrutoMesActual();
  }

  // Nuevo endpoint para el proceso de nómina mensual
  @GetMapping("/promedio-mensual")
  public BigDecimal getPromedioNominaMensual() {
    return planillaService.getPromedioSueldoBrutoMesActual();
  }

  @GetMapping
  public List<Planilla> getAllPlanillas() {
    return planillaService.getAllPlanillas();
  }

  @GetMapping("/boleta/{idPlanilla}/pdf")
public ResponseEntity<byte[]> descargarBoletaPDF(@PathVariable Integer idPlanilla) {
    try {
      Planilla planilla = planillaService.getPlanillaById(idPlanilla);
      if (planilla == null) {
        return ResponseEntity.notFound().build();
      }
     byte[] pdfBytes = planillaService.generarBoletaPDF(
    planilla.getIdUsuario(),
    planilla.getPeriodoMes(),
    planilla.getPeriodoAnio()
);
      // Log aquí:
      logger.info("PDF generado y descargado para la planilla con ID: {}", idPlanilla);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boleta_" + idPlanilla + ".pdf")
          .contentType(MediaType.APPLICATION_PDF)
          .body(pdfBytes);
    } catch (Exception e) {
      logger.error("Error al generar o descargar el PDF para la planilla con ID: {}", idPlanilla, e);
      return ResponseEntity.internalServerError().build();
    }
  }

  // Endpoint para solo generar el PDF (no lo descarga)
  @PostMapping("/boleta/{idPlanilla}/generar-pdf")
  public ResponseEntity<String> generarBoletaPDF(@PathVariable Integer idPlanilla) {
    try {
      Planilla planilla = planillaService.getPlanillaById(idPlanilla);
      if (planilla == null) {
        return ResponseEntity.notFound().build();
      }
      byte[] pdfBytes = planillaService.generarBoletaPDF(
            planilla.getIdUsuario(),
            planilla.getPeriodoMes(),
            planilla.getPeriodoAnio()
        );

      // NUEVO: Guarda la boleta si no existe
      if (!boletaRepository.existsByPlanilla(planilla)) {
        Boleta boleta = new Boleta();
        boleta.setPlanilla(planilla);
        boleta.setUsuario(planilla.getUsuario());
        boleta.setFecha_generacion(LocalDate.now());
        boleta.setRuta_archivo("http://localhost:8085/api/planillas/boleta/" + planilla.getIdPlanilla() + "/pdf");
        boletaRepository.save(boleta);
      }

      logger.info("PDF generado para la planilla con ID: {}", idPlanilla);
      return ResponseEntity.ok("PDF generado para la planilla " + idPlanilla);
    } catch (Exception e) {
      logger.error("Error al generar el PDF para la planilla con ID: {}", idPlanilla, e);
      return ResponseEntity.internalServerError().body("Error al generar el PDF");
    }
  }

  @GetMapping("/empleados-con-planilla")
  public List<EmpleadoConUltimaPlanillaDTO> listarEmpleadosConUltimaPlanilla() {
    List<User> usuarios = userRepository.findAll();
    List<EmpleadoConUltimaPlanillaDTO> resultado = new ArrayList<>();

    for (User usuario : usuarios) {
      Planilla ultimaPlanilla = planillaRepository
        .findTopByUsuarioIdUsuarioOrderByFechaGeneracionDescIdPlanillaDesc(usuario.getIdUsuario());

      if (ultimaPlanilla != null) { // Solo agrega empleados con planilla
        // Agrega el log solicitado aquí:
        logger.info("Última planilla encontrada para {}: id_planilla = {}", usuario.getCorreo(), ultimaPlanilla.getIdPlanilla());

        EmpleadoConUltimaPlanillaDTO dto = new EmpleadoConUltimaPlanillaDTO();
        dto.setId_usuario(usuario.getIdUsuario());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreo(usuario.getCorreo());
        dto.setCargo(usuario.getCargo());
        dto.setRol(usuario.getRol());
        dto.setEstado(usuario.getEstado());
        dto.setIdPlanilla(ultimaPlanilla.getIdPlanilla());

        resultado.add(dto);
      }
    }
    return resultado;
  }
}
