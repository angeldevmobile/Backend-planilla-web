package com.example.SmartPayroll.services;

import com.example.SmartPayroll.models.Contrato;
import com.example.SmartPayroll.models.Planilla;
import com.example.SmartPayroll.repositories.ContratoRepository;
import com.example.SmartPayroll.repositories.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal; 

@Service
public class PlanillaService {

    private static final Logger logger = LoggerFactory.getLogger(PlanillaService.class); // Logger initialization

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ResultadoDescuentosService resultadoDescuentosService;

    @Autowired
    private PlanillaRepository planillaRepository;

    /**
     * Procesa la planilla: busca el contrato vigente, calcula los descuentos y guarda la planilla.
     * @return La planilla guardada con los cálculos, o null si no se encontró un contrato vigente.
     */
    public Planilla procesarYGuardarPlanilla(Planilla planilla) { // Cambiado el tipo de retorno
        Contrato contrato = contratoRepository.findContratoVigenteByIdUsuario(planilla.getIdUsuario(), LocalDate.now());

        if (contrato != null) {

            // Asegurar que la planilla tenga el sueldo bruto del contrato si no se envió en el request
            // y el contrato tiene un sueldo bruto.
            if (planilla.getSueldoBruto() == null && contrato.getSueldoBruto() != null) {
                planilla.setSueldoBruto(contrato.getSueldoBruto());
            } else if (planilla.getSueldoBruto() == null && contrato.getSueldoBruto() == null) {
                 logger.error("Sueldo bruto es NULL tanto en la planilla de entrada como en el contrato encontrado para el usuario ID: {}. No se puede calcular la planilla.", planilla.getIdUsuario());
                 // Considera lanzar una excepción o devolver null indicando que no se puede procesar.
                 return null; // O manejar de otra forma, ya que sin sueldo bruto el cálculo es problemático.
            }

            ResultadoDescuentos resultado = resultadoDescuentosService.calcularDescuentos(contrato);

            planilla.setTotalDescuentos(resultado.getTotalDescuentos());
            planilla.setSueldoNeto(resultado.getSueldoNeto());
            // Aquí también podrías querer asignar el ID del contrato a la planilla si tienes un campo para ello.
            // Ejemplo: planilla.setIdContrato(contrato.getId_contrato());

            return planillaRepository.save(planilla);
        } else {
            logger.warn("No se encontró contrato vigente para el usuario ID: {}", planilla.getIdUsuario());
            return null; // Devuelve null si no se encontró contrato
        }
    }

    // --- MÉTODO PARA OBTENER EL TOTAL DE SUELDO BRUTO DEL MES ACTUAL ---
    public BigDecimal getTotalSueldoBrutoMesActual() {
        int mes = java.time.LocalDate.now().getMonthValue();
        int anio = java.time.LocalDate.now().getYear();
        return planillaRepository.getTotalSueldoBrutoByMesAndAnio(mes, anio);
    }

    // --- MÉTODO PARA OBTENER EL PROMEDIO DE SUELDO BRUTO DEL MES ACTUAL ---
    public BigDecimal getPromedioSueldoBrutoMesActual() {
        int mes = java.time.LocalDate.now().getMonthValue();
        int anio = java.time.LocalDate.now().getYear();
        return planillaRepository.getPromedioSueldoBrutoByMesAndAnio(mes, anio);
    }

    public List<Planilla> getAllPlanillas() {
        return planillaRepository.findAll();
    }
}