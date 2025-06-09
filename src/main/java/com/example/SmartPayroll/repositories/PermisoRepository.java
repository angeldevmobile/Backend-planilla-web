package com.example.SmartPayroll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SmartPayroll.models.Permisos;

public interface PermisoRepository extends JpaRepository<Permisos, Long> {
}
