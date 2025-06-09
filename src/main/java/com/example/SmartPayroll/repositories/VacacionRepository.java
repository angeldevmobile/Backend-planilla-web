package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.models.Vacaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VacacionRepository extends JpaRepository<Vacaciones, Integer> {
  List<Vacaciones> findByIdUsuario(int idUsuario);
}