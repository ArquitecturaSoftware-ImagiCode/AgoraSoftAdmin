package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.OrganizationModulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationModuloRepository extends JpaRepository<OrganizationModulo, Long> {

    // Encontrar por organización y módulo
    Optional<OrganizationModulo> findByOrganizationIdAndModuloId(Long organizationId, Long moduloId);

    // Encontrar todos los módulos de una organización
    List<OrganizationModulo> findByOrganizationId(Long organizationId);

    // Encontrar módulos activos de una organización
    List<OrganizationModulo> findByOrganizationIdAndActivoTrue(Long organizationId);

    // Encontrar organizaciones que tienen un módulo específico activo
    List<OrganizationModulo> findByModuloIdAndActivoTrue(Long moduloId);

    // Verificar si una organización tiene un módulo activo
    boolean existsByOrganizationIdAndModuloIdAndActivoTrue(Long organizationId, Long moduloId);

    // Contar organizaciones que tienen un módulo activo
    @Query("SELECT COUNT(om) FROM OrganizationModulo om WHERE om.modulo.id = :moduloId AND om.activo = true")
    long countOrganizacionesActivasByModuloId(@Param("moduloId") Long moduloId);
}