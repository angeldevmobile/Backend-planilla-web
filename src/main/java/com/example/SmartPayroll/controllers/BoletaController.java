package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Boleta;
import com.example.SmartPayroll.repositories.BoletaRepository;
import com.example.SmartPayroll.dto.BoletaDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

  @Autowired
  private BoletaRepository boletaRepository;

  @GetMapping("/{idUsuario}")
  public List<BoletaDTO> getBoletasPorUsuario(@PathVariable Integer idUsuario) {
    List<Boleta> boletas = boletaRepository.findByUsuarioId(idUsuario);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // <-- Cambia aquí

    return boletas.stream().map(b -> {
      int mesNum = b.getPlanilla() != null && b.getPlanilla().getPeriodo_mes() != null ? b.getPlanilla().getPeriodo_mes() : 1;
      String nombreMes = Month.of(mesNum).getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();

      int periodoAnio = b.getPlanilla() != null && b.getPlanilla().getPeriodo_anio() != null ? b.getPlanilla().getPeriodo_anio() : 0;
      String fechaGeneracion = b.getFecha_generacion() != null ? b.getFecha_generacion().format(formatter) : "";
      String rutaArchivo = b.getRuta_archivo() != null ? b.getRuta_archivo() : "";

      return new BoletaDTO(
          b.getId_boleta(),
          nombreMes,
          periodoAnio,
          fechaGeneracion,
          rutaArchivo
      );
    }).collect(Collectors.toList());
  }
}