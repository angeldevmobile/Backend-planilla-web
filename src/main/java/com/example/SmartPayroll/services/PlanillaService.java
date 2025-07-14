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
import java.util.Locale;
import java.math.BigDecimal;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.ByteArrayOutputStream;

@Service
public class PlanillaService {

    private static final Logger logger = LoggerFactory.getLogger(PlanillaService.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ResultadoDescuentosService resultadoDescuentosService;

    @Autowired
    private PlanillaRepository planillaRepository;

    public Planilla procesarYGuardarPlanilla(Planilla planilla) {
        Contrato contrato = contratoRepository.findContratoVigenteByIdUsuario(planilla.getIdUsuario(), LocalDate.now());

        if (contrato != null) {
            if (planilla.getSueldoBruto() == null && contrato.getSueldoBruto() != null) {
                planilla.setSueldoBruto(contrato.getSueldoBruto());
            } else if (planilla.getSueldoBruto() == null && contrato.getSueldoBruto() == null) {
                logger.error("Sueldo bruto es NULL tanto en la planilla de entrada como en el contrato encontrado para el usuario ID: {}. No se puede calcular la planilla.", planilla.getIdUsuario());
                return null;
            }

            ResultadoDescuentos resultado = resultadoDescuentosService.calcularDescuentos(contrato);
            planilla.setTotalDescuentos(resultado.getTotalDescuentos());
            planilla.setSueldoNeto(resultado.getSueldoNeto());
            return planillaRepository.save(planilla);
        } else {
            logger.warn("No se encontró contrato vigente para el usuario ID: {}", planilla.getIdUsuario());
            return null;
        }
    }

    public BigDecimal getTotalSueldoBrutoMesActual() {
        int mes = java.time.LocalDate.now().getMonthValue() - 1;
        int anio = java.time.LocalDate.now().getYear();
        return planillaRepository.getTotalSueldoBrutoByMesAndAnio(mes, anio);
    }

    public byte[] generarBoletaPDF(Integer idUsuario, int periodoMes, int periodoAnio) throws Exception {
    Planilla planilla = planillaRepository
        .findByUsuarioIdUsuarioAndPeriodoMesAndPeriodoAnio(idUsuario, periodoMes, periodoAnio)
        .orElseThrow(() -> new RuntimeException("No se encontró planilla para el periodo especificado."));

    Document document = new Document(PageSize.A4.rotate()); // Hoja horizontal
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, baos);
    document.open();

    BigDecimal horasExtras = obtenerHorasExtraTotalesMes(idUsuario, periodoMes, periodoAnio);
    BigDecimal totalPagoHorasExtras = planilla.getBonificaciones() != null ? planilla.getBonificaciones() : BigDecimal.ZERO;

    // Bloque A: Encabezado con logo a la izquierda y texto centrado
PdfPTable encabezado = new PdfPTable(2);
encabezado.setWidthPercentage(100);
encabezado.setWidths(new float[]{1f, 4f});

// Celda de logo
try {
    Image logo = Image.getInstance("src/main/resources/static/images/logo_vitalis.png");
    logo.scaleToFit(100, 100);
    logo.setAlignment(Image.ALIGN_LEFT);
    document.add(logo);
    document.add(Chunk.NEWLINE);
} catch (Exception e) {
    logger.warn("No se pudo cargar el logo: " + e.getMessage());
}


// TEXTO DE EMPRESA CENTRADO
Paragraph textoEmpresa = new Paragraph();
textoEmpresa.setAlignment(Element.ALIGN_CENTER);
textoEmpresa.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
textoEmpresa.add(new Phrase("CLÍNICA VITALIS\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13)));
textoEmpresa.add(new Phrase("RUC 12345678901\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
textoEmpresa.add(new Phrase("Av. San Isidro - Lima\n\n", FontFactory.getFont(FontFactory.HELVETICA, 11)));
textoEmpresa.add(new Phrase("BOLETA DE PAGO\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
textoEmpresa.add(new Phrase("Mes: " + getNombreMes(periodoMes) + " " + periodoAnio, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
document.add(textoEmpresa);

document.add(Chunk.NEWLINE);

    

    // Línea horizontal como separación (bloque B)
    LineSeparator separator = new LineSeparator();
    separator.setLineColor(BaseColor.LIGHT_GRAY);
    separator.setLineWidth(1f);
    document.add(new Chunk(separator));
    document.add(Chunk.NEWLINE);

    // Bloque B: Datos del trabajador
    PdfPTable datos = new PdfPTable(6);
    datos.setWidthPercentage(100);
    datos.setWidths(new float[]{1.2f, 2f, 1f, 2f, 1.5f, 1.5f});
    datos.addCell(getCell("DNI:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(planilla.getUsuario().getDni(), PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell("Empleado:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(planilla.getUsuario().getNombres() + " " + planilla.getUsuario().getApellidos(), PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell("Fecha Ingreso:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(String.valueOf(planilla.getUsuario().getFecha_ingreso()), PdfPCell.ALIGN_LEFT));

    datos.addCell(getCell("Cargo:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(planilla.getUsuario().getCargo(), PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell("Días Trabajados:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(String.valueOf(planilla.getDiasAsistidos()), PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell("Turno:", PdfPCell.ALIGN_LEFT));
    datos.addCell(getCell(planilla.getUsuario().getTurno(), PdfPCell.ALIGN_LEFT));

    document.add(datos);
    document.add(Chunk.NEWLINE);

    // C: Tabla de Ingresos
    PdfPTable ingresos = new PdfPTable(3);
    ingresos.setWidthPercentage(100);
    ingresos.setWidths(new float[]{2f, 1.2f, 1.5f});
    ingresos.addCell(getHeaderCell("DESCRIPCIÓN"));
    ingresos.addCell(getHeaderCell("DÍAS/HORAS"));
    ingresos.addCell(getHeaderCell("INGRESOS"));

    ingresos.addCell("Sueldo Básico");
    ingresos.addCell("30");
    ingresos.addCell(format(planilla.getSueldoBruto()));

    ingresos.addCell("Horas Extras");
    ingresos.addCell(horasExtras.toPlainString());
    ingresos.addCell(format(totalPagoHorasExtras));

    ingresos.addCell("TOTAL INGRESOS");
    ingresos.addCell("");
    BigDecimal totalIngresos = planilla.getSueldoBruto().add(planilla.getBonificaciones());
    ingresos.addCell(format(totalIngresos));

    

    // D: Tabla de Descuentos
    PdfPTable descuentos = new PdfPTable(2);
    descuentos.setWidthPercentage(100);
    descuentos.setWidths(new float[]{2f, 1.5f});
    descuentos.addCell(getHeaderCell("DESCRIPCIÓN"));
    descuentos.addCell(getHeaderCell("DESCUENTOS"));

    descuentos.addCell("Descuento AFP");
    descuentos.addCell(format(planilla.getDescuentoAfp()));

    descuentos.addCell("Descuento ONP");
    descuentos.addCell(format(planilla.getDescuentoOnp()));

    descuentos.addCell("Inasistencias");
    descuentos.addCell(format(planilla.getTotalDescuentos()));

    descuentos.addCell("Salidas Anticipadas");
    descuentos.addCell(format(planilla.getDescuentoSalidasAnticipadas()));

    BigDecimal totalDescuentos = sum(planilla.getDescuentoAfp(), planilla.getDescuentoOnp(), planilla.getTotalDescuentos(), planilla.getDescuentoSalidasAnticipadas());
    descuentos.addCell("TOTAL DESCUENTOS");
    descuentos.addCell(format(totalDescuentos));

    // Combinar ingresos y descuentos en una sola fila horizontal
    PdfPTable tablaPrincipal = new PdfPTable(2);
    tablaPrincipal.setWidthPercentage(100);
    tablaPrincipal.setWidths(new float[]{1.5f, 1.5f});
    tablaPrincipal.addCell(new PdfPCell(ingresos));
    tablaPrincipal.addCell(new PdfPCell(descuentos));
    document.add(tablaPrincipal);

    // F: Importe Neto y Firma
    document.add(Chunk.NEWLINE);
    Paragraph neto = new Paragraph("IMPORTE NETO: S/. " + format(planilla.getSueldoNeto()),
            new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
    neto.setAlignment(Element.ALIGN_RIGHT);
    document.add(neto);

    document.add(Chunk.NEWLINE);
    document.add(new Paragraph("REPRESENTANTE LEGAL ___________________        FIRMA TRABAJADOR ____________________"));

    document.close();
    return baos.toByteArray();
}

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

    private String format(BigDecimal value) {
    return value != null ? String.format("%.2f", value) : "0.00";
}

private BigDecimal sum(BigDecimal... valores) {
    BigDecimal total = BigDecimal.ZERO;
    for (BigDecimal v : valores) {
        if (v != null) total = total.add(v);
    }
    return total;
}

private String getNombreMes(int mes) {
    return java.time.Month.of(mes)
            .getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"))
            .toUpperCase(); // opcional para MAYÚSCULAS
}

private PdfPCell getCell(String text, int alignment) {
    PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 9)));
    cell.setPadding(4);
    cell.setHorizontalAlignment(alignment);
    return cell;
}

private PdfPCell getHeaderCell(String text) {
    PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    return cell;
}
public BigDecimal obtenerHorasExtraTotalesMes(int usuarioId, int mes, int anio) {
    String sql = """
        SELECT SUM(horas_extra)
        FROM asistencias
        WHERE id_usuario = :usuarioId
          AND MONTH(fecha) = :mes
          AND YEAR(fecha) = :anio
          AND horas_extra IS NOT NULL
    """;

    Object resultado = entityManager.createNativeQuery(sql)
        .setParameter("usuarioId", usuarioId)
        .setParameter("mes", mes)
        .setParameter("anio", anio)
        .getSingleResult();

    if (resultado != null) {
        return new BigDecimal(resultado.toString());
    } else {
        return BigDecimal.ZERO;
    }
}
} 