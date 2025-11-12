package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByClerkOrgId(String clerkOrgId);
    List<Organization> findByActivoTrue();

    // Buscar por nombre
    @Query("SELECT o FROM Organization o WHERE o.nombre = :nombre")
    List<Organization> findByNombre(@Param("nombre") String nombre);

    // Buscar por OwnerId (propietario.id)
    @Query("SELECT o FROM Organization o WHERE o.propietario.id = :ownerId")
    List<Organization> findByOwnerId(@Param("ownerId") Long ownerId);

    // Buscar organizaciones activas
    @Query("SELECT o FROM Organization o WHERE o.activo = true")
    List<Organization> findActiveOrganizations();

    // Buscar organizaciones inactivas
    @Query("SELECT o FROM Organization o WHERE o.activo = false")
    List<Organization> findInactiveOrganizations();

    // Verificar si existe por ClerkOrgId
    boolean existsByClerkOrgId(String clerkOrgId);

    // Verificar si existe por nombre
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organization o WHERE o.nombre = :nombre")
    boolean existsByNombre(@Param("nombre") String nombre);
}
