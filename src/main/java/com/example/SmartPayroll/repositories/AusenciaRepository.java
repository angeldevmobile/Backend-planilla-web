package com.example.SmartPayroll.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SmartPayroll.models.Ausencia;

public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {
  List<Ausencia> findByIdUsuarioAndJustificadaFalse(Integer idUsuario);

  Optional<Ausencia> findByIdUsuarioAndFecha(Integer idUsuario, LocalDate fecha);

  List<Ausencia> findByIdUsuario(int idUsuario);

  int countByIdUsuarioAndFechaBetween(Integer idUsuario, LocalDate inicio, LocalDate fin);
@Query("SELECT a FROM Ausencia a WHERE MONTH(a.fecha) = :mes AND YEAR(a.fecha) = :anio AND a.idUsuario = :usuarioId")
List<Ausencia> findByUsuarioIdAndMesAndAnio(@Param("usuarioId") Integer usuarioId,
                                            @Param("mes") int mes,
                                            @Param("anio") int anio);

                                            List<Ausencia> findByIdUsuarioAndFechaBetween(int usuarioId, LocalDate inicio, LocalDate fin);
List<Ausencia> findByEstado(String estado);
}