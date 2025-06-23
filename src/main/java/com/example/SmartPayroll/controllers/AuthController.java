package com.example.SmartPayroll.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.services.AuthService;
import com.example.SmartPayroll.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      log.info("Solicitud de login recibida para usuario: {}", request.getUsername());
      User user = authService.authenticate(request.getUsername(), request.getPassword());

      UserDTO userDTO = new UserDTO(
          user.getIdUsuario(),
          user.getNombres(),
          user.getApellidos(),
          user.getDni(),
          user.getTelefono(),
          user.getCorreo(),
          user.getDireccion(),
          user.getRol(),
          user.getIdLogeo(),
          user.getCargo(),
          user.getFecha_ingreso(),
          user.getFecha_nacimiento());

      return ResponseEntity.ok(userDTO);
    } catch (BadCredentialsException e) {
      log.warn("Error de autenticación: {}", e.getMessage());
      return ResponseEntity.status(401).body(new ErrorResponse("Credenciales inválidas"));
    } catch (Exception e) {
      log.error("Error inesperado durante el login", e);
      return ResponseEntity.status(500).body(new ErrorResponse("Error interno del servidor"));
    }
  }

  // Clases internas
  static class LoginRequest {
    private String username;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    private String password;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    // Getters y setters
  }

  static class ErrorResponse {
    private final String message;

    public String getMessage() {
      return message;
    }

    public ErrorResponse(String message) {
      this.message = message;
    }
  }
}