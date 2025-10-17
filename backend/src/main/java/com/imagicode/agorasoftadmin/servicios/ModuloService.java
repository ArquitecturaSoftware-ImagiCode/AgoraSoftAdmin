package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.CrearModuloDTO;
import com.imagicode.agorasoftadmin.dto.ModuloDTO;
import com.imagicode.agorasoftadmin.entidades.Empleado;
import com.imagicode.agorasoftadmin.entidades.Modulo;
import com.imagicode.agorasoftadmin.repositorios.EmpleadoRepository;
import com.imagicode.agorasoftadmin.repositorios.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Crear nuevo módulo
     */
    @Transactional
    public ModuloDTO crearModulo(CrearModuloDTO dto, String empleadoClerkUserId) {
        // Verificar que el nombre no exista
        if (moduloRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("Ya existe un módulo con ese nombre");
        }

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("MANAGE_MODULES")) {
            throw new RuntimeException("No tiene permisos para crear módulos");
        }

        Modulo modulo = new Modulo(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getIcono(),
                dto.getPrecioMensual(),
                empleado);

        modulo = moduloRepository.save(modulo);

        return convertirADTO(modulo);
    }

    /**
     * Obtener todos los módulos
     */
    public List<ModuloDTO> obtenerTodosLosModulos() {
        return moduloRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener módulos activos
     */
    public List<ModuloDTO> obtenerModulosActivos() {
        return moduloRepository.findByActivoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener módulo por ID
     */
    public ModuloDTO obtenerModuloPorId(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));
        return convertirADTO(modulo);
    }

    /**
     * Actualizar módulo
     */
    @Transactional
    public ModuloDTO actualizarModulo(Long id, CrearModuloDTO dto, String empleadoClerkUserId) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("MANAGE_MODULES")) {
            throw new RuntimeException("No tiene permisos para actualizar módulos");
        }

        modulo.setNombre(dto.getNombre());
        modulo.setDescripcion(dto.getDescripcion());
        modulo.setIcono(dto.getIcono());
        modulo.setPrecioMensual(dto.getPrecioMensual());
        modulo.actualizar(empleado);

        modulo = moduloRepository.save(modulo);

        return convertirADTO(modulo);
    }

    /**
     * Activar/Desactivar módulo
     */
    @Transactional
    public ModuloDTO toggleActivoModulo(Long id, String empleadoClerkUserId) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("MANAGE_MODULES")) {
            throw new RuntimeException("No tiene permisos para modificar módulos");
        }

        modulo.setActivo(!modulo.getActivo());
        modulo.actualizar(empleado);

        modulo = moduloRepository.save(modulo);

        return convertirADTO(modulo);
    }

    /**
     * Obtener estadísticas de módulos más contratados
     */
    public List<Object[]> obtenerModulosMasContratados() {
        return moduloRepository.findModulosMasContratados();
    }

    /**
     * Convertir entidad a DTO
     */
    private ModuloDTO convertirADTO(Modulo modulo) {
        ModuloDTO dto = new ModuloDTO();
        dto.setId(modulo.getId());
        dto.setNombre(modulo.getNombre());
        dto.setDescripcion(modulo.getDescripcion());
        dto.setIcono(modulo.getIcono());
        dto.setPrecioMensual(modulo.getPrecioMensual());
        dto.setActivo(modulo.getActivo());
        dto.setFechaCreacion(modulo.getFechaCreacion());
        dto.setFechaActualizacion(modulo.getFechaActualizacion());

        if (modulo.getCreadoPor() != null) {
            dto.setCreadoPorId(modulo.getCreadoPor().getId());
            dto.setCreadoPorNombre(modulo.getCreadoPor().getNombreCompleto());
        }

        dto.setCantidadPlazasContratadas(modulo.getCantidadPlazasContratadas());

        return dto;
    }
}