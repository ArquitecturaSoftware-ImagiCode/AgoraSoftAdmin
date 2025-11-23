package com.imagicode.agorasoftadmin.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.servicios.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    // GET: traer todas las organizaciones
    @GetMapping
    public List<Organization> listarOrganizaciones() {
        return organizationService.obtenerTodasLasOrganizaciones();
    }

    // GET: traer organización por id
    @GetMapping("/{id}")
    public Optional<Organization> obtenerOrganizacionPorId(@PathVariable Integer id) {
        return organizationService.obtenerOrganizacionPorId(id);
    }

    // GET: traer organización por ClerkOrgId
    @GetMapping("/clerk/{clerkOrgId}")
    public Optional<Organization> obtenerOrganizacionPorClerkOrgId(@PathVariable String clerkOrgId) {
        return organizationService.obtenerOrganizacionPorClerkOrgId(clerkOrgId);
    }

    // POST: crear organización
    @PostMapping
    public Organization crearOrganizacion(@RequestBody Organization organization) {
        System.out.println("Creando organización: " + organization);
        return organizationService.crearOrganizacion(organization);
    }

    // PUT: actualizar organización
    @PutMapping("/{id}")
    public Organization actualizarOrganizacion(@PathVariable Integer id, @RequestBody Organization organization) {
        organization.setId(id);
        return organizationService.actualizarOrganizacion(organization);
    }

    // DELETE: eliminar organización
    @DeleteMapping("/{id}")
    public void eliminarOrganizacion(@PathVariable Integer id) {
        organizationService.eliminarOrganizacion(id);
    }

    // GET: traer organizaciones por nombre
    @GetMapping("/search/name/{name}")
    public List<Organization> buscarOrganizacionesPorNombre(@PathVariable String name) {
        return organizationService.obtenerOrganizacionesPorNombre(name);
    }

    // GET: traer organizaciones por owner
    @GetMapping("/owner/{ownerId}")
    public List<Organization> obtenerOrganizacionesPorOwner(@PathVariable Integer ownerId) {
        return organizationService.obtenerOrganizacionesPorOwner(ownerId);
    }

    // GET: traer organizaciones activas
    @GetMapping("/active")
    public List<Organization> obtenerOrganizacionesActivas() {
        return organizationService.obtenerOrganizacionesActivas();
    }

    // GET: traer organizaciones inactivas
    @GetMapping("/inactive")
    public List<Organization> obtenerOrganizacionesInactivas() {
        return organizationService.obtenerOrganizacionesInactivas();
    }

    // GET: verificar si existe por ClerkOrgId
    @GetMapping("/exists/clerk/{clerkOrgId}")
    public boolean existePorClerkOrgId(@PathVariable String clerkOrgId) {
        return organizationService.existePorClerkOrgId(clerkOrgId);
    }

    // GET: verificar si existe por nombre
    @GetMapping("/exists/name/{name}")
    public boolean existePorNombre(@PathVariable String name) {
        return organizationService.existePorNombre(name);
    }

    // PUT: activar organización
    @PutMapping("/{id}/activate")
    public Organization activarOrganizacion(@PathVariable Integer id) {
        return organizationService.activarOrganizacion(id);
    }

    // PUT: desactivar organización
    @PutMapping("/{id}/deactivate")
    public Organization desactivarOrganizacion(@PathVariable Integer id) {
        return organizationService.desactivarOrganizacion(id);
    }
}
