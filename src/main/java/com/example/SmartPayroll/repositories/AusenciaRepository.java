package com.example.SmartPayroll.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SmartPayroll.models.Ausencia;

public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {
  List<Ausencia> findByIdUsuarioAndJustificadaFalse(Integer idUsuario);

  Optional<Ausencia> findByIdUsuarioAndFecha(Integer idUsuario, LocalDate fecha);

  List<Ausencia> findByIdUsuario(int idUsuario);
}