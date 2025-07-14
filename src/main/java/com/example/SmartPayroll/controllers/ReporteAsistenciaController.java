package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.services.ReporteAsistenciaMesService;
import com.example.SmartPayroll.services.ReporteAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reporte-asistencia")
public class ReporteAsistenciaController {

    @Autowired
    private ReporteAsistenciaService reporteAsistenciaService;

    @Autowired
    private ReporteAsistenciaMesService reporteAsistenciaMesService; // ✅ Aquí estaba faltando

    @GetMapping("/{usuarioId}")
    public ResponseEntity<byte[]> descargarReporte(@PathVariable Integer usuarioId) {
        try {
            byte[] pdfBytes = reporteAsistenciaService.generarReportePDFPorUsuario(usuarioId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reporte_asistencia_usuario_" + usuarioId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/mes")
    public ResponseEntity<byte[]> generarReportePorMes(
        @RequestParam int mes,
        @RequestParam int anio,
        @RequestParam(required = false) Long idUsuario
    ) {
        try {
            byte[] archivo = reporteAsistenciaMesService.generarReporte(mes, anio, idUsuario);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("reporte_asistencia_" + mes + "_" + anio + ".pdf")
                    .build());

            return new ResponseEntity<>(archivo, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
