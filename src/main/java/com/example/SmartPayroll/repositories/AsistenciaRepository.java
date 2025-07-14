package com.example.SmartPayroll.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.SmartPayroll.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
  Optional<Asistencia> findByIdUsuarioAndFecha(int idUsuario, LocalDate fecha);

  List<Asistencia> findAllByIdUsuario(Integer idUsuario);

  List<Asistencia> findByIdUsuarioAndFechaBetween(Integer idUsuario, LocalDate desde, LocalDate hasta);
@Query("SELECT a FROM Asistencia a WHERE MONTH(a.fecha) = :mes AND YEAR(a.fecha) = :anio AND a.idUsuario = :usuarioId")
List<Asistencia> findByUsuarioIdAndMesAndAnio(@Param("usuarioId") Integer usuarioId,
                                              @Param("mes") int mes,
                                              @Param("anio") int anio);

}