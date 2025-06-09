package com.example.SmartPayroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SmartPayroll.models.Planilla;

import java.math.BigDecimal;

public interface PlanillaRepository extends JpaRepository<Planilla, Integer> {
    @Query("SELECT COALESCE(SUM(p.sueldoBruto), 0) FROM Planilla p WHERE p.periodo_mes = :mes AND p.periodo_anio = :anio")
    BigDecimal getTotalSueldoBrutoByMesAndAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);

    @Query("SELECT COALESCE(AVG(p.sueldoBruto), 0) FROM Planilla p WHERE p.periodo_mes = :mes AND p.periodo_anio = :anio")
    BigDecimal getPromedioSueldoBrutoByMesAndAnio(@Param("mes") Integer mes, @Param("anio") Integer anio);
}