package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Buscar por correo
    Optional<Usuario> findByCorreo(String correo);

    // Verificar existencia por correo
    boolean existsByCorreo(String correo);

    // Buscar por rol
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    java.util.List<Usuario> findByRol(@Param("rol") String rol);

    // Buscar por organización
    @Query("SELECT u FROM Usuario u WHERE u.organizacion = :organizacion")
    java.util.List<Usuario> findByOrganizacion(@Param("organizacion") String organizacion);

    // Verificar si es empleado
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
            "FROM Empleado e WHERE e.usuario.id = :clerkUserId")
    boolean esEmpleado(@Param("clerkUserId") String clerkUserId);

    // Verificar si es representante de plaza
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Plaza p WHERE p.usuario.id = :clerkUserId")
    boolean esRepresentantePlaza(@Param("clerkUserId") String clerkUserId);
}