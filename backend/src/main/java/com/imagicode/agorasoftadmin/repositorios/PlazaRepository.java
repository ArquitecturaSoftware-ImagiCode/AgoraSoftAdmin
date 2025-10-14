package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Plaza;
import com.imagicode.agorasoftadmin.entidades.EstadoPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlazaRepository extends JpaRepository<Plaza, Long> {

    // Buscar por RUT
    Optional<Plaza> findByRut(String rut);

    // Buscar por clerkUserId del representante
    @Query("SELECT p FROM Plaza p WHERE p.usuario.id = :clerkUserId")
    Optional<Plaza> findByClerkUserId(@Param("clerkUserId") String clerkUserId);

    // Verificar existencia por RUT
    boolean existsByRut(String rut);

    // Listar por estado
    List<Plaza> findByEstado(EstadoPlaza estado);

    // Listar plazas pendientes de aprobación
    @Query("SELECT p FROM Plaza p WHERE p.estado = 'PENDIENTE' ORDER BY p.fechaRegistro ASC")
    List<Plaza> findPlazasPendientes();

    // Listar plazas activas
    List<Plaza> findByEstadoOrderByFechaRegistroDesc(EstadoPlaza estado);

    // Buscar por nombre (búsqueda parcial)
    @Query("SELECT p FROM Plaza p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Plaza> findByNombreContaining(@Param("nombre") String nombre);

    // Buscar por representante legal
    @Query("SELECT p FROM Plaza p WHERE LOWER(p.representanteLegal) LIKE LOWER(CONCAT('%', :representante, '%'))")
    List<Plaza> findByRepresentanteLegalContaining(@Param("representante") String representante);

    // Búsqueda general (nombre, rut, representante)
    @Query("SELECT p FROM Plaza p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.rut) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.representanteLegal) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Plaza> searchPlazas(@Param("searchTerm") String searchTerm);

    // Contar por estado
    long countByEstado(EstadoPlaza estado);

    // Plazas registradas en un rango de fechas
    @Query("SELECT p FROM Plaza p WHERE p.fechaRegistro BETWEEN :fechaInicio AND :fechaFin")
    List<Plaza> findByFechaRegistroBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Plazas aprobadas por un empleado
    @Query("SELECT p FROM Plaza p WHERE p.aprobadoPor.id = :empleadoId")
    List<Plaza> findPlazasAprobadasPorEmpleado(@Param("empleadoId") Long empleadoId);

    // Últimas plazas registradas (Top N)
    @Query("SELECT p FROM Plaza p ORDER BY p.fechaRegistro DESC")
    List<Plaza> findTopNPlazasRecientes();
}