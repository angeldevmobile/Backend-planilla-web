package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.dto.ViewEmployeeAdDTO;
import com.example.SmartPayroll.models.Planilla;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.repositories.PlanillaRepository;
import com.example.SmartPayroll.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/empleados")
public class ViewEmpleadoAdController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PlanillaRepository planillaRepository;

    @GetMapping
    public List<ViewEmployeeAdDTO> listarEmpleados() {
        List<User> empleados = usuarioRepository.findAll(); // o filtrar por rol
        List<ViewEmployeeAdDTO> dtoList = new ArrayList<>();

        for (User empleado : empleados) {
            ViewEmployeeAdDTO dto = new ViewEmployeeAdDTO();
            dto.setId_usuario(empleado.getIdUsuario()); // ← ESTO ES CLAVE
            dto.setNombres(empleado.getNombres());
            dto.setApellidos(empleado.getApellidos());
            dto.setCorreo(empleado.getCorreo());
            dto.setCargo(empleado.getCargo());
            dto.setRol(empleado.getRol());
            dto.setEstado(empleado.getEstado());

            Planilla ultimaPlanilla = planillaRepository
                    .findTopByUsuarioIdUsuarioOrderByFechaGeneracionDescIdPlanillaDesc(empleado.getIdUsuario());
            if (ultimaPlanilla != null) {
                dto.setId_planilla(ultimaPlanilla.getIdPlanilla());
            }

            dtoList.add(dto);
        }
        return dtoList;
    }

}