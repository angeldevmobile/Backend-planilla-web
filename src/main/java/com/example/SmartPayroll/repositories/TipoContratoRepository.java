package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.models.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Integer> {
}
