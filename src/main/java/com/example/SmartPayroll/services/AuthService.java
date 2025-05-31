package com.example.SmartPayroll.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.example.SmartPayroll.models.User;
import com.example.SmartPayroll.repositories.UserRepositoy;
@Slf4j
@Service
public class AuthService {
    private final UserRepositoy userRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean devMode = true; // Cambiar a false en producción

    public AuthService(UserRepositoy userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password) {
        log.info("Intento de autenticación para usuario: {}", username);

        User user = userRepository.findByIdLogeo(username)
            .orElseThrow(() -> {
                log.warn("Usuario no encontrado: {}", username);
                return new BadCredentialsException("Credenciales inválidas");
            });

        log.debug("Usuario encontrado: {}", user.getNombres());

        if (devMode) {
            // Modo desarrollo: acepta BCrypt o texto plano
            if (!checkPassword(password, user.getPassword())) {
                log.warn("Contraseña incorrecta para usuario: {}", username);
                throw new BadCredentialsException("Credenciales inválidas");
            }
            log.warn("AUTENTICACIÓN CON TEXTO PLANO - ACTUALIZAR CONTRASEÑA PARA: {}", username);
        } else {
            // Modo producción: solo BCrypt
            if (!passwordEncoder.matches(password, user.getPassword())) {
                log.warn("Contraseña incorrecta para usuario: {}", username);
                throw new BadCredentialsException("Credenciales inválidas");
            }
        }

        log.info("Autenticación exitosa para usuario: {}", username);
        return user;
    }

    private boolean checkPassword(String rawPassword, String storedPassword) {
        // Si la contraseña almacenada parece un hash BCrypt
        if (storedPassword.startsWith("$2a$")) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        // Comparación directa para texto plano
        return storedPassword.equals(rawPassword);
    }
}




// package com.example.SmartPayroll.services;

// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import lombok.extern.slf4j.Slf4j;
// import com.example.SmartPayroll.models.User;
// import com.example.SmartPayroll.repositories.UserRepositoy;

// @Slf4j
// @Service
// public class AuthService {
//   private final UserRepositoy userRepository;
//   private final PasswordEncoder passwordEncoder;

//   public AuthService(UserRepositoy userRepository, PasswordEncoder passwordEncoder) {
//     this.userRepository = userRepository;
//     this.passwordEncoder = passwordEncoder;
//   }

//   public User authenticate(String username, String password) {
//     log.info("Intento de autenticación para usuario: {}", username);

//     User user = userRepository.findByIdLogeo(username)
//         .orElseThrow(() -> {
//           log.warn("Usuario no encontrado: {}", username);
//           return new BadCredentialsException("Credenciales inválidas");
//         });

//     log.debug("Usuario encontrado: {}", user.getNombres());

//     if (!passwordEncoder.matches(password, user.getPassword())) {
//       log.warn("Contraseña incorrecta para usuario: {}", username);
//       throw new BadCredentialsException("Credenciales inválidas");
//     }

//     log.info("Autenticación exitosa para usuario: {}", username);
//     return user;
//   }
// }