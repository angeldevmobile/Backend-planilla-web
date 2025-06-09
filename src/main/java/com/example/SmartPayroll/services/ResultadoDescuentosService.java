package com.example.SmartPayroll.services;

import com.example.SmartPayroll.models.Contrato;
import com.example.SmartPayroll.models.ContratoDescuento;
import com.example.SmartPayroll.models.Descuento;
import com.example.SmartPayroll.models.TipoContrato; // Asegúrate de importar TipoContrato
import com.example.SmartPayroll.repositories.ContratoDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger; // Para logging
import org.slf4j.LoggerFactory; // Para logging

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultadoDescuentosService {

    private static final Logger logger = LoggerFactory.getLogger(ResultadoDescuentosService.class); // Para logging

    @Autowired
    private ContratoDescuentoRepository contratoDescuentoRepository;

    public ResultadoDescuentos calcularDescuentos(Contrato contrato) {
        logger.info("Iniciando cálculo de descuentos para contrato ID: {}", contrato != null ? contrato.getId_contrato() : "null");

        if (contrato == null) {
            logger.error("El objeto Contrato es nulo. No se pueden calcular descuentos.");
            // Devuelve un resultado vacío o lanza una excepción, según tu manejo de errores.
            return new ResultadoDescuentos(BigDecimal.ZERO, new ArrayList<>(), BigDecimal.ZERO);
        }

        BigDecimal sueldoBruto = contrato.getSueldoBruto();
        if (sueldoBruto == null) {
            logger.warn("Sueldo bruto es nulo para el contrato ID: {}. Se asumirá 0 para el cálculo de sueldo neto.", contrato.getId_contrato());
            sueldoBruto = BigDecimal.ZERO; // O manejar como error si el sueldo bruto es indispensable
        }
        // logger.info("Sueldo Bruto del contrato: {}", sueldoBruto);

        TipoContrato tipoContrato = contrato.getTipoContrato();
        if (tipoContrato == null) {
            logger.error("TipoContrato es nulo para el contrato ID: {}. No se pueden buscar relaciones de descuento.", contrato.getId_contrato());
            return new ResultadoDescuentos(sueldoBruto, new ArrayList<>(), BigDecimal.ZERO); // Sueldo neto sería igual al bruto sin descuentos
        }
        logger.info("Calculando descuentos para TipoContrato ID: {}", tipoContrato.getId_tipo_contrato());

        List<ContratoDescuento> relaciones = contratoDescuentoRepository.findByTipoContrato(tipoContrato);
        logger.info("Número de relaciones ContratoDescuento encontradas: {}", relaciones.size());

        List<Descuento> descuentosAplicados = new ArrayList<>();
        BigDecimal totalDescuentos = BigDecimal.ZERO;

        for (ContratoDescuento rel : relaciones) {
            Descuento descuento = rel.getDescuento();
            if (descuento != null) {
                logger.info("Procesando Descuento ID: {}, Nombre: {}, Activo: {}, Valor: {}",
                   descuento.getIdDescuento(), descuento.getNombre(), descuento.getActivo(), descuento.getValor());
                if (descuento.getActivo() != null && descuento.getActivo() == 1 && descuento.getValor() != null) {
                    descuentosAplicados.add(descuento);
                    totalDescuentos = totalDescuentos.add(descuento.getValor());
                    logger.info("Descuento ID: {} APLICADO. Total descuentos acumulado: {}", descuento.getIdDescuento(), totalDescuentos);
                } else {
                    logger.info("Descuento ID: {} NO APLICADO. Condiciones no cumplidas (Activo: {}, Valor: {}).",
                       descuento.getIdDescuento(), descuento.getActivo(), descuento.getValor());
                }
            } else {
                logger.warn("Se encontró una relación ContratoDescuento con un Descuento nulo. ID Relación: {}", rel.getId());
            }
        }

        BigDecimal sueldoNeto = sueldoBruto.subtract(totalDescuentos);
        logger.info("Cálculo finalizado. Sueldo Neto: {}, Total Descuentos: {}", sueldoNeto, totalDescuentos);

        return new ResultadoDescuentos(sueldoNeto, descuentosAplicados, totalDescuentos);
    }
}
