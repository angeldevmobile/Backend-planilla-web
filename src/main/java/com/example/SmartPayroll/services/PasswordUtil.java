package com.example.SmartPayroll.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
  public static String generarPassword(String nombre, String dni){
    String basePassword = nombre.toLowerCase().replaceAll("\\s+", "") + dni.substring(dni.length() - 4);
    return new BCryptPasswordEncoder().encode(basePassword);
  }
}
