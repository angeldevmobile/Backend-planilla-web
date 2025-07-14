package com.example.SmartPayroll.services;

import com.example.SmartPayroll.repositories.AsistenciaReportRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.example.SmartPayroll.models.Asistencia;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.repositories.AsistenciaRepository;
import com.example.SmartPayroll.repositories.AusenciaRepository;
import com.example.SmartPayroll.repositories.UserRepository;

@Service
public class ReporteService {

    @Autowired
    private AsistenciaReportRepository asistenciaReportRepository;
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AusenciaRepository ausenciaRepository;

    @Autowired
    private UserRepository userRepository;

    public byte[] generarReporte() {
        try {
            // Fechas
            LocalDate hoy = LocalDate.now();
            YearMonth mesActual = YearMonth.from(hoy);
            YearMonth mesAnterior = mesActual.minusMonths(1);

            LocalDate inicioAnterior = mesAnterior.atDay(1);
            LocalDate finAnterior = mesAnterior.atEndOfMonth();
            LocalDate inicioActual = mesActual.atDay(1);
            LocalDate finActual = mesActual.atEndOfMonth();

            // Generar PDF
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reporte de Asistencia por Empleado", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setWidths(new float[]{4, 2, 2, 2, 2, 2});

            String[] headers = {"Empleado", "Asist. Anterior", "Faltas Ant.", "Asist. Actual", "Faltas Act.", "Asistencia %"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            List<Object[]> resumen = asistenciaReportRepository.obtenerResumenAsistencia(
                    inicioAnterior, finAnterior, inicioActual, finActual
            );

            int diasHabilesActual = contarDiasHabiles(mesActual);
            for (Object[] fila : resumen) {
                String nombre = (String) fila[1];
                int asistAnt = ((Number) fila[2]).intValue();
                int faltasAnt = ((Number) fila[3]).intValue();
                int asistAct = ((Number) fila[4]).intValue();
                int faltasAct = ((Number) fila[5]).intValue();

                double porcentaje = diasHabilesActual > 0 ? ((double) asistAct / diasHabilesActual) * 100 : 0.0;

                table.addCell(nombre);
                table.addCell(String.valueOf(asistAnt));
                table.addCell(String.valueOf(faltasAnt));
                table.addCell(String.valueOf(asistAct));
                table.addCell(String.valueOf(faltasAct));
                table.addCell(String.format("%.1f%%", porcentaje));
            }

            document.add(table);
            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            System.err.println("❌ Error generando el reporte: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private int contarDiasHabiles(YearMonth mes) {
        int total = 0;
        for (int dia = 1; dia <= mes.lengthOfMonth(); dia++) {
            LocalDate fecha = mes.atDay(dia);
            if (!(fecha.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                  fecha.getDayOfWeek() == java.time.DayOfWeek.SUNDAY)) {
                total++;
            }
        }
        return total;
    }

/*public byte[] generarReporteMensualPorUsuario(int usuarioId) {
    try {
        YearMonth mesActual = YearMonth.now();
        LocalDate inicio = mesActual.atDay(1);
        LocalDate fin = mesActual.atEndOfMonth();

        List<Asistencia> asistencias = asistenciaRepository
                .findByUsuarioIdAndFechaBetween(usuarioId, inicio, fin);

        int totalAusencias = ausenciaRepository
                .countByUsuarioIdAndFechaBetween(usuarioId, inicio, fin);

        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Reporte Mensual de Asistencias", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Empleado: " + user.getNombres() + " " + user.getApellidos()));
        document.add(new Paragraph("Mes: " + mesActual.getMonth() + " " + mesActual.getYear()));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{4, 4});
        table.addCell("Fecha");
        table.addCell("Hora de Entrada");

        for (Asistencia asistencia : asistencias) {
            table.addCell(asistencia.getFecha().toString());
            table.addCell(asistencia.getHoraIngreso().toString());
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total de Ausencias en el Mes: " + totalAusencias));

        document.close();
        return baos.toByteArray();

    } catch (Exception e) {
        System.err.println("❌ Error generando reporte individual: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}
    */
}