package com.example.SmartPayroll.controllers;

import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PutMapping("/{idLogeo}")
  public ResponseEntity<?> actualizarUsuario(@PathVariable String idLogeo, @RequestBody User datosActualizados) {
    Optional<User> optionalUser = userRepository.findByIdLogeo(idLogeo);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User user = optionalUser.get();
    user.setTelefono(datosActualizados.getTelefono());
    user.setDireccion(datosActualizados.getDireccion());
    userRepository.save(user);

    return ResponseEntity.ok("Usuario actualizado correctamente");
  }

  @GetMapping("/{idLogeo}")
  public ResponseEntity<?> obtenerUsuario(@PathVariable String idLogeo) {
    Optional<User> optionalUser = userRepository.findByIdLogeo(idLogeo);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(optionalUser.get());
  }
}