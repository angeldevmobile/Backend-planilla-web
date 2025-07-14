package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.services.ReporteService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/reporte-asistencia")
public ResponseEntity<byte[]> generarReportePDF() {
    byte[] pdfBytes = reporteService.generarReporte();

    if (pdfBytes == null || pdfBytes.length == 0) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al generar el reporte PDF".getBytes());
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition
            .builder("attachment")
            .filename("reporte_asistencia.pdf")
            .build());

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
}
}