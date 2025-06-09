package com.example.SmartPayroll.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SmartPayroll.dto.AdRegisterEmployeeDTO;
import com.example.SmartPayroll.repositories.ContactoEmergenciaRepository;
import com.example.SmartPayroll.repositories.ContratoRepository;
import com.example.SmartPayroll.repositories.PlanillaRepository;
import com.example.SmartPayroll.repositories.TipoContratoRepository;
import com.example.SmartPayroll.repositories.UserRepositoy;
import com.example.SmartPayroll.services.PasswordUtil;

@Service
public class RegisterEmployee {

  @Autowired
  private UserRepositoy userRepository;

  @Autowired
  private ContratoRepository contratoRepository;

  @Autowired
  private PlanillaRepository planillaRepository;

  @Autowired
  private ContactoEmergenciaRepository contactoEmergenciaRepository;

  @Autowired
  private TipoContratoRepository tipoContratoRepository;

  @Transactional
  public void registerEmployee(AdRegisterEmployeeDTO dto) {

    // Guardar el usuario
    User user = new User();
    user.setNombres(dto.nombres);
    user.setApellidos(dto.apellidos);
    user.setDni(dto.dni);
    user.setTelefono(dto.telefono);
    user.setCorreo(dto.correo);
    user.setDireccion(dto.direccion);
    user.setRol(dto.rol);
    user.setCargo(dto.cargo);
    user.setEstado(dto.estado);
    user.setSexo(dto.sexo);
    user.setFecha_nacimiento(dto.fecha_nacimiento);
    user.setTurno(dto.turno);
    user.setFecha_ingreso(dto.fechaIngreso);

    String passwordGenerada = PasswordUtil.generarPassword(dto.nombres, dto.dni);
    user.setPassword(passwordGenerada);

    userRepository.save(user);

    // Guardar contrato
    Contrato contrato = new Contrato();
    contrato.setIdUsuario(user.getId_usuario());

    // Buscar el TipoContrato por ID
    TipoContrato tipoContrato = tipoContratoRepository.findById(dto.idTipoContrato)
        .orElseThrow(() -> new IllegalArgumentException("TipoContrato no encontrado: " + dto.idTipoContrato));
    contrato.setTipoContrato(tipoContrato);

    // Convertir strings a LocalDate
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    contrato.setFechaInicio(LocalDate.parse(dto.fechaInicioContrato, formatter));

    // Validar antes de parsear fechaFinContrato
    if (dto.fechaFinContrato != null && !dto.fechaFinContrato.trim().isEmpty()) {
        contrato.setFecha_fin(LocalDate.parse(dto.fechaFinContrato, formatter));
    } else {
        contrato.setFecha_fin(null); 
    }

    contrato.setSueldoBruto(dto.sueldoBruto);
    contrato.setCondiciones(dto.condicionesContrato);
    contratoRepository.save(contrato);

    // Guardar planilla
    Planilla planilla = new Planilla();
    planilla.setUsuario(user);
    planilla.setPeriodo_mes(dto.periodoMes);
    planilla.setPeriodo_anio(dto.periodoAnio);
    planilla.setSueldoBruto(dto.sueldoBruto);
    planilla.setBonificaciones(dto.bonificaciones);
    planillaRepository.save(planilla);

    // Guardar contacto de emergencia
    ContactoEmergencia contacto = new ContactoEmergencia();
    contacto.setIdUsuario(user.getId_usuario());
    contacto.setNombreContacto(dto.nombreContacto);
    contacto.setTelefonoContacto(dto.telefonoContacto);
    contacto.setParentesco(dto.parentescoContacto);
    contacto.setDireccionContacto(dto.direccionContacto);
    contactoEmergenciaRepository.save(contacto);
  }
}
