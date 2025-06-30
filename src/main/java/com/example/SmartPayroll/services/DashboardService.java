package com.example.SmartPayroll.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SmartPayroll.dto.AsistenciaMensualDTO;
import com.example.SmartPayroll.models.DashboardStatsDTO;
import com.example.SmartPayroll.repositories.DashboardRepository;

@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardStatsDTO obtenerEstadisticas(int idUsuario) {
        String porcentaje = dashboardRepository.getPorcentajeAsistenciaMesPasado(idUsuario);
        int vacaciones = dashboardRepository.getVacacionesMesPasado(idUsuario);
        int ausencias = dashboardRepository.getAusenciasMesActual(idUsuario);
        int asistencias = dashboardRepository.getAsistenciasMesActual(idUsuario);

        return new DashboardStatsDTO(porcentaje + "%", vacaciones, ausencias, asistencias);
    }

    public List<AsistenciaMensualDTO> getAsistenciasMensuales(int idUsuario) {
    return dashboardRepository.getAsistenciasPorMes(idUsuario);
}
}
