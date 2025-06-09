package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.models.ContratoDescuento;
import com.example.SmartPayroll.models.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContratoDescuentoRepository extends JpaRepository<ContratoDescuento, Integer> {
    List<ContratoDescuento> findByTipoContrato(TipoContrato tipoContrato);
}
