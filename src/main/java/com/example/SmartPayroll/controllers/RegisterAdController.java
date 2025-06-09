package com.example.SmartPayroll.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartPayroll.dto.AdRegisterEmployeeDTO;
import com.example.SmartPayroll.models.RegisterEmployee;

@RestController
@RequestMapping("/api/register-employee-add")
public class RegisterAdController {
  
  @Autowired 
  private RegisterEmployee registerEmployee;

  @PostMapping
  public ResponseEntity<?> registerEmployee(@RequestBody AdRegisterEmployeeDTO dto){
    registerEmployee.registerEmployee(dto);
    return ResponseEntity.ok("Empleado registrado correctamente");
  }
}
