package com.example.SmartPayroll.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.SmartPayroll.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
  Optional<Asistencia> findByIdUsuarioAndFecha(int idUsuario, LocalDate fecha);

  List<Asistencia> findAllByIdUsuario(Integer idUsuario);
}