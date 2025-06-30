package com.example.SmartPayroll.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartPayroll.dto.AsistenciaMensualDTO;
import com.example.SmartPayroll.dto.AusenciasPieDTO;
import com.example.SmartPayroll.models.DashboardStatsDTO;
import com.example.SmartPayroll.repositories.DashboardRepository;
import com.example.SmartPayroll.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard-stats")


public class DashboardController {
        private final DashboardService dashboardService;
        private final DashboardRepository dashboardRepository;

    public DashboardController(DashboardService dashboardService, DashboardRepository dashboardRepository) {
    this.dashboardService = dashboardService;
    this.dashboardRepository = dashboardRepository;
}

    @GetMapping("/{idUsuario}")
    public ResponseEntity<DashboardStatsDTO> obtenerEstadisticas(@PathVariable int idUsuario) {
        DashboardStatsDTO stats = dashboardService.obtenerEstadisticas(idUsuario);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/mensual/{idUsuario}")
    public ResponseEntity<List<AsistenciaMensualDTO>> getAsistenciasMensuales(@PathVariable int idUsuario) {
    List<AsistenciaMensualDTO> lista = dashboardService.getAsistenciasMensuales(idUsuario);
    return ResponseEntity.ok(lista);

}

@GetMapping("/ausencias-mes/{idUsuario}")
public ResponseEntity<AusenciasPieDTO> getAusenciasJustificadas(@PathVariable int idUsuario) {
    int[] valores = dashboardRepository.getAusenciasJustificadasYNo(idUsuario);
    return ResponseEntity.ok(new AusenciasPieDTO(valores[0], valores[1]));
}

}
