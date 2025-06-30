package com.example.SmartPayroll.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.SmartPayroll.dto.AsistenciaMensualDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DashboardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public String getPorcentajeAsistenciaMesPasado(int idUsuario) {
         System.out.println("Consultando porcentaje asistencia para ID: " + idUsuario);
        String sql = """
            SELECT ROUND(COUNT(*) / 30 * 100, 0)
            FROM asistencias
            WHERE id_usuario = :idUsuario
              AND MONTH(fecha) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)
              AND YEAR(fecha) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
        """;
        Object result = entityManager.createNativeQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
        return result != null ? result.toString() : "0";
    }

    public int getVacacionesMesPasado(int idUsuario) {
        String sql = """
            SELECT COUNT(*) 
            FROM vacaciones
            WHERE id_usuario = :idUsuario
              AND (
                MONTH(fecha_inicio) = MONTH(CURRENT_DATE )
                OR MONTH(fecha_fin) = MONTH(CURRENT_DATE )
              )
        """;
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult()).intValue();
    }

    public int getAusenciasMesActual(int idUsuario) {
        String sql = """
            SELECT COUNT(*) 
            FROM ausencias 
            WHERE id_usuario = :idUsuario
              AND MONTH(fecha) = MONTH(CURRENT_DATE)
              AND YEAR(fecha) = YEAR(CURRENT_DATE)
        """;
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult()).intValue();
    }

    public int getAsistenciasMesActual(int idUsuario) {
        String sql = """
            SELECT COUNT(*) 
            FROM asistencias 
            WHERE id_usuario = :idUsuario
              AND MONTH(fecha) = MONTH(CURRENT_DATE)
              AND YEAR(fecha) = YEAR(CURRENT_DATE)
        """;
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult()).intValue();
    }

    public List<AsistenciaMensualDTO> getAsistenciasPorMes(int idUsuario) {
    String sql = """
        SELECT MONTH(fecha), YEAR(fecha), COUNT(*) 
        FROM asistencias 
        WHERE id_usuario = :idUsuario 
        GROUP BY YEAR(fecha), MONTH(fecha) 
        ORDER BY YEAR(fecha) DESC, MONTH(fecha) DESC
    """;

    @SuppressWarnings("unchecked")
    List<Object[]> results = (List<Object[]>) entityManager.createNativeQuery(sql)
        .setParameter("idUsuario", idUsuario)
        .getResultList();

    return results.stream()
        .map(row -> new AsistenciaMensualDTO(
            ((Number) row[0]).intValue(), // mes
            ((Number) row[1]).intValue(), // anio
            ((Number) row[2]).intValue()  // total_asistencias
        ))
        .toList();
}

public int[] getAusenciasJustificadasYNo(int idUsuario) {
    String sql = """
        SELECT
            SUM(CASE WHEN justificada = 1 THEN 1 ELSE 0 END) AS justificadas,
            SUM(CASE WHEN justificada = 0 THEN 1 ELSE 0 END) AS no_justificadas
        FROM ausencias
        WHERE id_usuario = :idUsuario
          AND MONTH(fecha) = MONTH(CURRENT_DATE)
          AND YEAR(fecha) = YEAR(CURRENT_DATE)
    """;

    Object[] result = (Object[]) entityManager.createNativeQuery(sql)
        .setParameter("idUsuario", idUsuario)
        .getSingleResult();

    int justificadas = ((Number) result[0]).intValue();
    int noJustificadas = ((Number) result[1]).intValue();

    return new int[]{justificadas, noJustificadas};
}
}
