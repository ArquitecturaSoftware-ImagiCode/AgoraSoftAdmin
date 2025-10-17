package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    // Buscar por nombre
    Optional<Modulo> findByNombre(String nombre);

    // Verificar existencia por nombre
    boolean existsByNombre(String nombre);

    // Listar módulos activos
    List<Modulo> findByActivoTrue();

    // Listar módulos inactivos
    List<Modulo> findByActivoFalse();

    // Listar todos ordenados por nombre
    List<Modulo> findAllByOrderByNombreAsc();

    // Buscar por nombre (búsqueda parcial)
    @Query("SELECT m FROM Modulo m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Modulo> findByNombreContaining(@Param("nombre") String nombre);

    // Módulos creados por un empleado
    @Query("SELECT m FROM Modulo m WHERE m.creadoPor.id = :empleadoId")
    List<Modulo> findModulosCreadosPorEmpleado(@Param("empleadoId") Long empleadoId);

    // Módulos más contratados (con cantidad de plazas)
    @Query("SELECT m, COUNT(pm.id) as cantidad FROM Modulo m " +
            "LEFT JOIN m.plazasConModulo pm " +
            "WHERE pm.estado = 'ACTIVO' " +
            "GROUP BY m.id " +
            "ORDER BY cantidad DESC")
    List<Object[]> findModulosMasContratados();

    // Contar módulos activos
    long countByActivoTrue();
}