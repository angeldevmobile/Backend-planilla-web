package com.example.SmartPayroll.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartPayroll.models.Planilla;
import com.example.SmartPayroll.services.PlanillaService;

@RestController
@RequestMapping("/api/planillas")
public class PlanillaController {

  @Autowired
  private PlanillaService planillaService;

  // Genera y guarda la planilla para un usuario específico
  @PostMapping("/generar/{idUsuario}")
  public String generarPlanillaUsuario(@PathVariable Integer idUsuario, @RequestBody Planilla planilla) {
    planilla.setUsuarioId(idUsuario);
    planillaService.procesarYGuardarPlanilla(planilla);
    return "Planilla generada para el usuario " + idUsuario;
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
}