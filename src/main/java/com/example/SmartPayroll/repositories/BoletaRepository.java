package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.models.Boleta;
import com.example.SmartPayroll.models.Planilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {

  @Query("SELECT b FROM Boleta b WHERE b.planilla.usuario.idUsuario = :idUsuario")
  List<Boleta> findByUsuarioId(@Param("idUsuario") Integer idUsuario);

  boolean existsByPlanilla(Planilla planilla);

}
