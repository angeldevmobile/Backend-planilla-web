package com.example.SmartPayroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SmartPayroll.models.Contrato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    @Query("SELECT c FROM Contrato c WHERE c.idUsuario = :idUsuario AND (c.fecha_fin IS NULL OR c.fecha_fin >= :fechaActual)")
    Contrato findContratoVigenteByIdUsuario(@Param("idUsuario") Integer idUsuario, @Param("fechaActual") LocalDate fechaActual);
}
