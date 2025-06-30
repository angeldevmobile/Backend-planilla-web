package com.example.SmartPayroll.repositories;

import com.example.SmartPayroll.dto.ViewEmployeeAdDTO;
import com.example.SmartPayroll.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ViewEmployeeAdRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new com.example.SmartPayroll.dto.ViewEmployeeAdDTO(u.idUsuario, u.nombres, u.apellidos, u.correo, u.cargo, u.rol, u.estado) FROM User u")

    List<ViewEmployeeAdDTO> getAllEmpleados();
}
