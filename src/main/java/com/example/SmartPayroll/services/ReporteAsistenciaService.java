package com.example.SmartPayroll.services;

import com.example.SmartPayroll.models.Asistencia;
import com.example.SmartPayroll.models.Ausencia;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.repositories.AsistenciaRepository;
import com.example.SmartPayroll.repositories.AusenciaRepository;
import com.example.SmartPayroll.repositories.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReporteAsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AusenciaRepository ausenciaRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public byte[] generarReportePDFPorUsuario(int usuarioId) throws Exception {
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        YearMonth mesActual = YearMonth.now();
        LocalDate inicioMes = mesActual.atDay(1);
        LocalDate finMes = mesActual.atEndOfMonth();

        List<Asistencia> asistencias = asistenciaRepository.findByIdUsuarioAndFechaBetween(usuarioId, inicioMes, finMes);
        List<Ausencia> ausencias = ausenciaRepository.findByIdUsuarioAndFechaBetween(usuarioId, inicioMes, finMes);

        Map<LocalDate, Asistencia> asistenciaPorFecha = asistencias.stream()
                .collect(Collectors.toMap(Asistencia::getFecha, a -> a));
        Map<LocalDate, Ausencia> ausenciaPorFecha = ausencias.stream()
                .collect(Collectors.toMap(Ausencia::getFecha, a -> a));

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, baos);
        document.open();

        try {
            // Agregar logo
            Image logo = Image.getInstance(getClass().getClassLoader().getResource("static/images/logo_vitalis.png"));
            logo.scaleToFit(80, 80);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            // Si falla el logo, continúa sin él
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Planilla de Asistencia - Clínica Vitalis", titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(15);
        document.add(titulo);

        Locale localeEs = new Locale("es", "ES");
        String nombreMes = mesActual.getMonth().getDisplayName(java.time.format.TextStyle.FULL, localeEs).toUpperCase();

        document.add(new Paragraph("Empleado: " + usuario.getNombres() + " " + usuario.getApellidos()));
        document.add(new Paragraph("Mes: " + nombreMes + " " + mesActual.getYear()));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2.5f, 2, 2, 2, 2, 2, 2});

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Stream.of("Fecha", "Entrada", "Salida", "Asistencia", "Ausencia", "Horas Extra", "Salida Anticipada")
                .forEach(col -> {
                    PdfPCell header = new PdfPCell(new Phrase(col, headerFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(header);
                });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        int totalAsistencias = 0;
        int totalAusencias = 0;

        String horaSalidaTeorica = obtenerHoraSalidaTeoricaPorTurno(usuario.getTurno());

        for (LocalDate fecha = inicioMes; !fecha.isAfter(finMes); fecha = fecha.plusDays(1)) {
            if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) continue;

            tabla.addCell(new PdfPCell(new Phrase(fecha.format(formatter), fontNormal)));

            Asistencia asistencia = asistenciaPorFecha.get(fecha);
            Ausencia ausencia = ausenciaPorFecha.get(fecha);

            String entrada = (asistencia != null && asistencia.getHoraIngreso() != null) ? asistencia.getHoraIngreso() : "-";
            String salida = (asistencia != null && asistencia.getHoraSalida() != null) ? asistencia.getHoraSalida() : "-";

            PdfPCell cellEntrada = new PdfPCell(new Phrase(entrada, fontNormal));
            PdfPCell cellSalida = new PdfPCell(new Phrase(salida, fontNormal));
            cellEntrada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellSalida.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(cellEntrada);
            tabla.addCell(cellSalida);

            String asistenciaStr = "";
            String ausenciaStr = "";

            if (asistencia != null) {
                asistenciaStr = "/";
                totalAsistencias++;
            } else if (fecha.isBefore(LocalDate.now())) {
                ausenciaStr = "X";
                totalAusencias++;
            }

            PdfPCell cellAsistencia = new PdfPCell(new Phrase(asistenciaStr, fontNormal));
            PdfPCell cellAusencia = new PdfPCell(new Phrase(ausenciaStr, fontNormal));
            cellAsistencia.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cellAusencia.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(cellAsistencia);
            tabla.addCell(cellAusencia);

            String horasExtraStr = (asistencia != null && asistencia.getHorasExtra() != null && asistencia.getHorasExtra().compareTo(BigDecimal.ZERO) > 0)
                    ? asistencia.getHorasExtra().toPlainString()
                    : "-";
            PdfPCell cellHorasExtra = new PdfPCell(new Phrase(horasExtraStr, fontNormal));
            cellHorasExtra.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(cellHorasExtra);

            String salidaAnticipadaStr = "-";
            if (asistencia != null && asistencia.getHoraSalida() != null && horaSalidaTeorica != null) {
                try {
                    java.time.LocalTime salidaReal = java.time.LocalTime.parse(asistencia.getHoraSalida());
                    java.time.LocalTime salidaEsperada = java.time.LocalTime.parse(horaSalidaTeorica);
                    if (salidaReal.isBefore(salidaEsperada)) {
                        long minutos = java.time.Duration.between(salidaReal, salidaEsperada).toMinutes();
                        salidaAnticipadaStr = String.format("%.2f", minutos / 60.0);
                    }
                } catch (Exception e) {
                    salidaAnticipadaStr = "-";
                }
            }
            PdfPCell cellSalidaAnticipada = new PdfPCell(new Phrase(salidaAnticipadaStr, fontNormal));
            cellSalidaAnticipada.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(cellSalidaAnticipada);
        }

        document.add(tabla);
        document.add(Chunk.NEWLINE);

        Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        document.add(new Paragraph("Total días asistidos: " + totalAsistencias, totalFont));
        document.add(new Paragraph("Total días de ausencia: " + totalAusencias, totalFont));

        document.close();
        return baos.toByteArray();
    }

    private String obtenerHoraSalidaTeoricaPorTurno(String turno) {
        String clave = switch (turno.toUpperCase()) {
            case "MAÑANA" -> "HORA_SALIDA_MAÑANA";
            case "TARDE" -> "HORA_SALIDA_TARDE";
            case "NOCHE" -> "HORA_SALIDA_NOCHE";
            default -> throw new IllegalArgumentException("Turno desconocido: " + turno);
        };

        Object resultado = entityManager.createNativeQuery("SELECT valor FROM parametros_laborales WHERE clave = :clave")
                .setParameter("clave", clave)
                .getSingleResult();

        return resultado != null ? resultado.toString() : null;
    }
}