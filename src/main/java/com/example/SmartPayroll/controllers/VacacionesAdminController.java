package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.Vacaciones;
import com.example.SmartPayroll.repositories.VacacionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/vacaciones")
public class VacacionesAdminController {

    @Autowired
    private VacacionRepository vacacionRepository;

    // Listar todas las solicitudes de vacaciones
    @GetMapping("/todas")
    public List<Vacaciones> obtenerTodasLasSolicitudes() {
        return vacacionRepository.findAll();
    }

    // Aprobar o rechazar una solicitud de vacaciones
    @PutMapping("/revisar/{idVacacion}")
    public ResponseEntity<?> revisarSolicitud(
            @PathVariable int idVacacion,
            @RequestParam("aprobado") String aprobado,
            @RequestParam(value = "observaciones", required = false) String observaciones) {
        try {
            Vacaciones vacacion = vacacionRepository.findById(idVacacion).orElse(null);
            if (vacacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada");
            }
            vacacion.setAprobado(aprobado); // "Aprobado" o "Rechazado"
            vacacion.setObservaciones(observaciones);
            vacacionRepository.save(vacacion);
            return ResponseEntity.ok(vacacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al revisar la solicitud: " + e.getMessage());
        }
    }
}
