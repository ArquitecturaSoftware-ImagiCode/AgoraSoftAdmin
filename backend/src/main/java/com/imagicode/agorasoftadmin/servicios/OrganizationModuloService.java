package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.OrganizationModuloDTO;
import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.entidades.OrganizationModulo;
import com.imagicode.agorasoftadmin.entidades.Modulo;
import com.imagicode.agorasoftadmin.repositorios.OrganizationModuloRepository;
import com.imagicode.agorasoftadmin.repositorios.OrganizationRepository;
import com.imagicode.agorasoftadmin.repositorios.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationModuloService {

    @Autowired
    private OrganizationModuloRepository organizationModuloRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    /**
     * Asignar un módulo a una organización
     */
    @Transactional
    public OrganizationModuloDTO asignarModuloAOrganization(Long organizationId, Long moduloId, 
                                                           Long adminId, String adminNombre) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        Modulo modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        // Verificar si ya existe la relación
        OrganizationModulo existing = organizationModuloRepository
                .findByOrganizationIdAndModuloId(organizationId, moduloId)
                .orElse(null);

        if (existing != null) {
            // Si ya existe, actualizar
            existing.setActivo(true);
            existing.setActivadoPorAdminId(adminId);
            existing.setActivadoPorAdminNombre(adminNombre);
            existing = organizationModuloRepository.save(existing);
        } else {
            // Si no existe, crear nueva relación
            existing = new OrganizationModulo(organization, modulo, true, adminId, adminNombre);
            existing = organizationModuloRepository.save(existing);
        }

        return convertirADTO(existing);
    }

    /**
     * Desactivar módulo de una organización
     */
    @Transactional
    public OrganizationModuloDTO desactivarModuloDeOrganization(Long organizationId, Long moduloId,
                                                               Long adminId, String adminNombre) {
        OrganizationModulo organizationModulo = organizationModuloRepository
                .findByOrganizationIdAndModuloId(organizationId, moduloId)
                .orElseThrow(() -> new RuntimeException("Relación organización-módulo no encontrada"));

        organizationModulo.setActivo(false);
        organizationModulo.setActivadoPorAdminId(adminId);
        organizationModulo.setActivadoPorAdminNombre(adminNombre);

        organizationModulo = organizationModuloRepository.save(organizationModulo);

        return convertirADTO(organizationModulo);
    }

    /**
     * Obtener todos los módulos de una organización
     */
    public List<OrganizationModuloDTO> obtenerModulosDeOrganization(Long organizationId) {
        return organizationModuloRepository.findByOrganizationId(organizationId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener módulos activos de una organización
     */
    public List<OrganizationModuloDTO> obtenerModulosActivosDeOrganization(Long organizationId) {
        return organizationModuloRepository.findByOrganizationIdAndActivoTrue(organizationId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convertir entidad a DTO
     */
    private OrganizationModuloDTO convertirADTO(OrganizationModulo organizationModulo) {
        OrganizationModuloDTO dto = new OrganizationModuloDTO();
        dto.setId(organizationModulo.getId());
        dto.setOrganizationId(organizationModulo.getOrganization().getId());
        dto.setOrganizationNombre(organizationModulo.getOrganization().getNombre());
        dto.setModuloId(organizationModulo.getModulo().getId());
        dto.setModuloNombre(organizationModulo.getModulo().getNombre());
        dto.setModuloDescripcion(organizationModulo.getModulo().getDescripcion());
        dto.setModuloIcono(organizationModulo.getModulo().getIcono());
        dto.setActivo(organizationModulo.getActivo());
        dto.setFechaAsignacion(organizationModulo.getFechaAsignacion());
        dto.setFechaActualizacion(organizationModulo.getFechaActualizacion());
        dto.setActivadoPorAdminId(organizationModulo.getActivadoPorAdminId());
        dto.setActivadoPorAdminNombre(organizationModulo.getActivadoPorAdminNombre());

        return dto;
    }
}