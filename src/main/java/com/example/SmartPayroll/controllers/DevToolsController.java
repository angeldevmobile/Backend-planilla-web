package com.example.SmartPayroll.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SmartPayroll.repositories.UserRepositoy;

@RestController
@RequestMapping("/api/dev-tools")
@Profile("dev")
public class DevToolsController {

  private final UserRepositoy userRepository;
  private final PasswordEncoder passwordEncoder;

  public DevToolsController(UserRepositoy userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/hash-passwords")
  public String hashAllPasswords() {
    userRepository.findAll().forEach(user -> {
      if (!user.getPassword().startsWith("$2a$")) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
      }
    });
    return "Todas las contraseñas hasheadas";
  }
}
