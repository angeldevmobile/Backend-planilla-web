package com.example.SmartPayroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SmartPayroll.models.Planilla;

import java.math.BigDecimal;
import java.util.Optional;

public interface PlanillaRepository extends JpaRepository<Planilla, Integer> {

    // Total mensual
    @Query("SELECT COALESCE(SUM(p.sueldoBruto), 0) FROM Planilla p WHERE p.periodoMes = :mes AND p.periodoAnio = :anio")
    BigDecimal getTotalSueldoBrutoByMesAndAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);

    // Promedio mensual
    @Query("SELECT COALESCE(AVG(p.sueldoBruto), 0) FROM Planilla p WHERE p.periodoMes = :mes AND p.periodoAnio = :anio")
    BigDecimal getPromedioSueldoBrutoByMesAndAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);

    // Última planilla de un usuario
    Planilla findTopByUsuarioIdUsuarioOrderByFechaGeneracionDescIdPlanillaDesc(Integer idUsuario);

    Optional<Planilla> findByUsuarioIdUsuarioAndPeriodoMesAndPeriodoAnio(Integer idUsuario, int mes, int anio);

 
}
