package com.imagicode.agorasoftadmin.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.repositorios.OrganizationRepository;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> obtenerTodasLasOrganizaciones() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> obtenerOrganizacionPorId(Integer id) {
        return organizationRepository.findById(id);
    }

    public Optional<Organization> obtenerOrganizacionPorClerkOrgId(String clerkOrgId) {
        return organizationRepository.findByClerkOrgId(clerkOrgId);
    }

    public Organization crearOrganizacion(Organization organization) {
        organization.setCreatedAt(LocalDateTime.now());
        return organizationRepository.save(organization);
    }

    public Organization actualizarOrganizacion(Organization organization) {
        organization.setUpdatedAt(LocalDateTime.now());
        return organizationRepository.save(organization);
    }

    public void eliminarOrganizacion(Integer id) {
        organizationRepository.deleteById(id);
    }

    public List<Organization> obtenerOrganizacionesPorNombre(String name) {
        return organizationRepository.findByName(name);
    }

    public List<Organization> obtenerOrganizacionesPorOwner(Integer ownerId) {
        return organizationRepository.findByOwnerId(ownerId);
    }

    public List<Organization> obtenerOrganizacionesActivas() {
        return organizationRepository.findActiveOrganizations();
    }

    public List<Organization> obtenerOrganizacionesInactivas() {
        return organizationRepository.findInactiveOrganizations();
    }

    public boolean existePorClerkOrgId(String clerkOrgId) {
        return organizationRepository.existsByClerkOrgId(clerkOrgId);
    }

    public boolean existePorNombre(String name) {
        return organizationRepository.existsByName(name);
    }

    public Organization activarOrganizacion(Integer id) {
        Optional<Organization> orgOpt = organizationRepository.findById(id);
        if (orgOpt.isPresent()) {
            Organization organization = orgOpt.get();
            organization.setIsActive(true);
            organization.setUpdatedAt(LocalDateTime.now());
            return organizationRepository.save(organization);
        }
        return null;
    }

    public Organization desactivarOrganizacion(Integer id) {
        Optional<Organization> orgOpt = organizationRepository.findById(id);
        if (orgOpt.isPresent()) {
            Organization organization = orgOpt.get();
            organization.setIsActive(false);
            organization.setUpdatedAt(LocalDateTime.now());
            return organizationRepository.save(organization);
        }
        return null;
    }
}
