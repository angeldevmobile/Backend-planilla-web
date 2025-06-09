package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.models.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DescuentoRepository extends JpaRepository<Descuento, Integer> {
    Optional<Descuento> findByNombre(String nombre);
}
