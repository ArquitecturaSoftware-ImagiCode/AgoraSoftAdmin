package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    // Buscar por ClerkOrgId
    Optional<Organization> findByClerkOrgId(String clerkOrgId);

    // Buscar por nombre
    @Query("SELECT o FROM Organization o WHERE o.name = :name")
    List<Organization> findByName(@Param("name") String name);

    // Buscar por OwnerId
    @Query("SELECT o FROM Organization o WHERE o.ownerId = :ownerId")
    List<Organization> findByOwnerId(@Param("ownerId") Integer ownerId);

    // Buscar organizaciones activas
    @Query("SELECT o FROM Organization o WHERE o.isActive = true")
    List<Organization> findActiveOrganizations();

    // Buscar organizaciones inactivas
    @Query("SELECT o FROM Organization o WHERE o.isActive = false")
    List<Organization> findInactiveOrganizations();

    // Verificar si existe por ClerkOrgId
    boolean existsByClerkOrgId(String clerkOrgId);

    // Verificar si existe por nombre
    boolean existsByName(String name);
}
