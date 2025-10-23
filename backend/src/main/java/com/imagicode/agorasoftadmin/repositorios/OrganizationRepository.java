package com.imagicode.agorasoftadmin.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.Organization;

import java.util.Optional;
import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByClerkOrgId(String clerkOrgId);
    List<Organization> findByActivoTrue();
}
