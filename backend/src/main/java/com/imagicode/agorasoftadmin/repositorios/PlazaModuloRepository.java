package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.PlazaModulo;
import com.imagicode.agorasoftadmin.entidades.EstadoModulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlazaModuloRepository extends JpaRepository<PlazaModulo, Long> {

    // Listar módulos de una plaza
    @Query("SELECT pm FROM PlazaModulo pm WHERE pm.plaza.id = :plazaId")
    List<PlazaModulo> findByPlazaId(@Param("plazaId") Long plazaId);

    // Listar módulos activos de una plaza
    @Query("SELECT pm FROM PlazaModulo pm WHERE pm.plaza.id = :plazaId AND pm.estado = :estado")
    List<PlazaModulo> findByPlazaIdAndEstado(@Param("plazaId") Long plazaId,
            @Param("estado") EstadoModulo estado);

    // Listar plazas que tienen un módulo específico
    @Query("SELECT pm FROM PlazaModulo pm WHERE pm.modulo.id = :moduloId")
    List<PlazaModulo> findByModuloId(@Param("moduloId") Long moduloId);

    // Verificar si una plaza tiene un módulo específico
    @Query("SELECT CASE WHEN COUNT(pm) > 0 THEN true ELSE false END " +
            "FROM PlazaModulo pm WHERE pm.plaza.id = :plazaId AND pm.modulo.id = :moduloId")
    boolean existsByPlazaIdAndModuloId(@Param("plazaId") Long plazaId,
            @Param("moduloId") Long moduloId);

    // Buscar relación específica entre plaza y módulo
    @Query("SELECT pm FROM PlazaModulo pm WHERE pm.plaza.id = :plazaId AND pm.modulo.id = :moduloId")
    Optional<PlazaModulo> findByPlazaIdAndModuloId(@Param("plazaId") Long plazaId,
            @Param("moduloId") Long moduloId);

    // Contar módulos activos de una plaza
    @Query("SELECT COUNT(pm) FROM PlazaModulo pm WHERE pm.plaza.id = :plazaId AND pm.estado = 'ACTIVO'")
    long countModulosActivosByPlazaId(@Param("plazaId") Long plazaId);

    // Módulos activados por un empleado
    @Query("SELECT pm FROM PlazaModulo pm WHERE pm.activadoPor.id = :empleadoId")
    List<PlazaModulo> findModulosActivadosPorEmpleado(@Param("empleadoId") Long empleadoId);
}