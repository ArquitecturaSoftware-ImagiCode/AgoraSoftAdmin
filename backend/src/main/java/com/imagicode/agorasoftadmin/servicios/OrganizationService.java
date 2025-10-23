package com.imagicode.agorasoftadmin.servicios;


import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.repositorios.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizacionsRepository;

    public OrganizationService(OrganizationRepository organizacionsRepository) {
        this.organizacionsRepository = organizacionsRepository;
    }

    // 🔹 Obtener todas las organizaciones
    public List<Organization> obtenerOrganizaciones() {
        return organizacionsRepository.findAll();
    }

    // 🔹 Obtener una organización por ID
    public Optional<Organization> obtenerOrganizacionPorId(Long id) {
        return organizacionsRepository.findById(id);
    }

    // 🔹 Obtener una organización por su ClerkOrgId
    public Optional<Organization> obtenerPorClerkOrgId(String clerkOrgId) {
        return organizacionsRepository.findByClerkOrgId(clerkOrgId);
    }

    // 🔹 Crear una nueva organización
    public Organization crearOrganizacion(Organization organizacion) {
        return organizacionsRepository.save(organizacion);
    }

    // 🔹 Actualizar una organización existente
    public Organization actualizarOrganizacion(Organization organizacion) {
        return organizacionsRepository.save(organizacion);
    }

    // 🔹 Eliminar una organización por ID
    public void eliminarOrganizacion(Long id) {
        organizacionsRepository.deleteById(id);
    }

    // 🔹 Obtener todas las organizaciones activas
    public List<Organization> obtenerOrganizacionesActivas() {
        return organizacionsRepository.findByActivoTrue();
    }

    // 🔹 Cambiar el estado activo/inactivo
    public Organization cambiarEstadoOrganizacion(Long id, boolean activo) {
        Optional<Organization> optionalOrg = organizacionsRepository.findById(id);
        if (optionalOrg.isPresent()) {
            Organization organizacion = optionalOrg.get();
            organizacion.setActivo(activo);
            return organizacionsRepository.save(organizacion);
        }
        return null;
    }
}