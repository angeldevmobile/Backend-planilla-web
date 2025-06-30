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
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;

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
        int mes = java.time.LocalDate.now().getMonthValue() - 1;
        int anio = java.time.LocalDate.now().getYear();
        return planillaRepository.getTotalSueldoBrutoByMesAndAnio(mes, anio);
    }

    public byte[] generarBoletaPDF(Planilla planilla) throws Exception {
        logger.info("Generando PDF para la planilla con ID: {}", planilla.getIdPlanilla()); 
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        document.add(new Paragraph("Boleta de Pago", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        document.add(new Paragraph(""));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell("Empleado:");
        table.addCell(planilla.getUsuario().getNombres());
        table.addCell("Periodo:");
        table.addCell(planilla.getPeriodoMes() + "/" + planilla.getPeriodoAnio());
        table.addCell("Fecha de generación:");
        table.addCell(planilla.getFechaGeneracion().toString());
        table.addCell("Sueldo Bruto:");
        table.addCell(planilla.getSueldoBruto().toString());
        table.addCell("Bonificaciones:");
        table.addCell(planilla.getBonificaciones().toString());
        table.addCell("Total Descuentos:");
        table.addCell(planilla.getTotalDescuentos().toString());
        table.addCell("Sueldo Neto:");
        table.addCell(planilla.getSueldoNeto().toString());
        document.add(table);
        document.close();
        logger.info("PDF generado correctamente para la planilla con ID: {}", planilla.getIdPlanilla()); 
        return baos.toByteArray();
    }

    // --- MÉTODO PARA OBTENER EL PROMEDIO DE SUELDO BRUTO DEL MES ACTUAL ---
    public BigDecimal getPromedioSueldoBrutoMesActual() {
        int mes = java.time.LocalDate.now().getMonthValue() - 1;
        int anio = java.time.LocalDate.now().getYear();
        return planillaRepository.getPromedioSueldoBrutoByMesAndAnio(mes, anio);
    }

    public List<Planilla> getAllPlanillas() {
        return planillaRepository.findAll();
    }

    public Planilla getPlanillaById(Integer id) {
        return planillaRepository.findById(id).orElse(null);
    }
}