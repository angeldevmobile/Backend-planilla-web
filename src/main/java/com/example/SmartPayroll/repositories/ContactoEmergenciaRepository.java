package com.example.SmartPayroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.SmartPayroll.models.ContactoEmergencia;

public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {
  List<ContactoEmergencia> findByIdUsuario(Integer idUsuario);
}
