package com.example.SmartPayroll.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SmartPayroll.models.User;

public interface UserRepositoy extends JpaRepository<User, Integer> {
  Optional<User> findByIdLogeo(String idLogeo);
}
