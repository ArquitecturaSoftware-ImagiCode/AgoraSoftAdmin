package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Empleado;
import com.imagicode.agorasoftadmin.entidades.RolEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Buscar por correo
    Optional<Empleado> findByCorreo(String correo);

    // Buscar por usuario (clerkUserId)
    @Query("SELECT e FROM Empleado e WHERE e.usuario.id = :clerkUserId")
    Optional<Empleado> findByClerkUserId(@Param("clerkUserId") String clerkUserId);

    // Verificar si existe por correo
    boolean existsByCorreo(String correo);

    // Listar empleados activos
    List<Empleado> findByActivoTrue();

    // Listar empleados inactivos
    List<Empleado> findByActivoFalse();

    // Listar por rol
    List<Empleado> findByRol(RolEmpleado rol);

    // Listar por departamento
    List<Empleado> findByDepartamento(String departamento);

    // Listar por rol y activo
    List<Empleado> findByRolAndActivo(RolEmpleado rol, Boolean activo);

    // Buscar por nombre o apellido (búsqueda parcial)
    @Query("SELECT e FROM Empleado e WHERE " +
            "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.correo) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Empleado> searchEmpleados(@Param("searchTerm") String searchTerm);

    // Contar empleados activos
    long countByActivoTrue();

    // Contar empleados por rol
    long countByRol(RolEmpleado rol);

    // Contar empleados por departamento
    long countByDepartamento(String departamento);
}