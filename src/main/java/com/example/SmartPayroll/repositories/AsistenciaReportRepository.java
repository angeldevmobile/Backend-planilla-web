package com.example.SmartPayroll.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AsistenciaReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Obtiene el resumen de asistencia por usuario para los meses anterior y actual.
     */
    public List<Object[]> obtenerResumenAsistencia(LocalDate inicioAnterior, LocalDate finAnterior,
                                                   LocalDate inicioActual, LocalDate finActual) {

        String sql = """
            SELECT 
                u.id_usuario,
                CONCAT(u.nombres, ' ', u.apellidos) AS nombre,

                -- Asistencias mes anterior
                (
                    SELECT COUNT(*) 
                    FROM asistencias a 
                    WHERE a.id_usuario = u.id_usuario
                      AND a.fecha BETWEEN :inicioAnterior AND :finAnterior
                      AND a.hora_entrada IS NOT NULL 
                      AND a.hora_salida IS NOT NULL
                ) AS asist_ant,

                -- Faltas mes anterior
                (
                    SELECT COUNT(*) 
                    FROM ausencias au 
                    WHERE au.id_usuario = u.id_usuario
                      AND au.fecha BETWEEN :inicioAnterior AND :finAnterior
                ) AS faltas_ant,

                -- Asistencias mes actual
                (
                    SELECT COUNT(*) 
                    FROM asistencias a 
                    WHERE a.id_usuario = u.id_usuario
                      AND a.fecha BETWEEN :inicioActual AND :finActual
                      AND a.hora_entrada IS NOT NULL 
                      AND a.hora_salida IS NOT NULL
                ) AS asist_act,

                -- Faltas mes actual
                (
                    SELECT COUNT(*) 
                    FROM ausencias au 
                    WHERE au.id_usuario = u.id_usuario
                      AND au.fecha BETWEEN :inicioActual AND :finActual
                ) AS faltas_act

            FROM usuarios u
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("inicioAnterior", inicioAnterior);
        query.setParameter("finAnterior", finAnterior);
        query.setParameter("inicioActual", inicioActual);
        query.setParameter("finActual", finActual);

        return query.getResultList();
    }
}