package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.*;
import com.imagicode.agorasoftadmin.entidades.Empleado;
import com.imagicode.agorasoftadmin.entidades.RolEmpleado;
import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.EmpleadoRepository;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClerkService clerkService; // Servicio para integración con Clerk

    /**
     * Crear un nuevo empleado
     */
    @Transactional
    public EmpleadoDTO crearEmpleado(CrearEmpleadoDTO dto) {
        // Verificar que el correo no exista
        if (empleadoRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un empleado con ese correo electrónico");
        }

        // Crear usuario en Clerk
        String clerkUserId = clerkService.crearUsuarioEmpleado(
                dto.getCorreo(),
                dto.getNombre(),
                dto.getApellido());

        // Crear entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setId(clerkUserId);
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol("EMPLEADO");
        usuario.setOrganizacion("AgoraSoft");
        usuario = usuarioRepository.save(usuario);

        // Crear entidad Empleado
        Empleado empleado = new Empleado();
        empleado.setUsuario(usuario);
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setCorreo(dto.getCorreo());
        empleado.setRol(dto.getRol());
        // Evitar insertar null en la columna 'departamento' (la tabla tiene constraint
        // NOT NULL).
        String departamento = dto.getDepartamento();
        if (departamento == null) {
            departamento = ""; // valor por defecto vacío para mantener compatibilidad con esquema
        }
        empleado.setDepartamento(departamento);
        empleado.setTelefono(dto.getTelefono());
        empleado.setActivo(true);

        // Asegurar que las fechas requeridas por el esquema no sean null
        if (empleado.getFechaContratacion() == null) {
            empleado.setFechaContratacion(LocalDate.now());
        }
        if (empleado.getFechaCreacion() == null) {
            empleado.setFechaCreacion(LocalDateTime.now());
        }

        empleado = empleadoRepository.save(empleado);

        // Enviar correo de bienvenida
        clerkService.enviarCorreoBienvenida(dto.getCorreo(), dto.getNombre());

        return convertirADTO(empleado);
    }

    /**
     * Obtener todos los empleados
     */
    public List<EmpleadoDTO> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener empleado por ID
     */
    public EmpleadoDTO obtenerEmpleadoPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertirADTO(empleado);
    }

    /**
     * Obtener empleado por clerkUserId
     */
    public EmpleadoDTO obtenerEmpleadoPorClerkUserId(String clerkUserId) {
        Empleado empleado = empleadoRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return convertirADTO(empleado);
    }

    /**
     * Actualizar empleado
     */
    @Transactional
    public EmpleadoDTO actualizarEmpleado(Long id, ActualizarEmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        if (dto.getNombre() != null) {
            empleado.setNombre(dto.getNombre());
        }

        if (dto.getApellido() != null) {
            empleado.setApellido(dto.getApellido());
        }

        if (dto.getRol() != null) {
            empleado.setRol(dto.getRol());
        }

        if (dto.getDepartamento() != null) {
            empleado.setDepartamento(dto.getDepartamento());
        }

        if (dto.getTelefono() != null) {
            empleado.setTelefono(dto.getTelefono());
        }

        if (dto.getActivo() != null) {
            empleado.setActivo(dto.getActivo());
        }

        empleado = empleadoRepository.save(empleado);
        return convertirADTO(empleado);
    }

    /**
     * Desactivar empleado
     */
    @Transactional
    public EmpleadoDTO desactivarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.desactivar();
        empleado = empleadoRepository.save(empleado);

        // Desactivar en Clerk también
        clerkService.desactivarUsuario(empleado.getUsuario().getId());

        return convertirADTO(empleado);
    }

    /**
     * Activar empleado
     */
    @Transactional
    public EmpleadoDTO activarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.activar();
        empleado = empleadoRepository.save(empleado);

        // Activar en Clerk también
        clerkService.activarUsuario(empleado.getUsuario().getId());

        return convertirADTO(empleado);
    }

    /**
     * Buscar empleados
     */
    public List<EmpleadoDTO> buscarEmpleados(String searchTerm) {
        return empleadoRepository.searchEmpleados(searchTerm).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener empleados activos
     */
    public List<EmpleadoDTO> obtenerEmpleadosActivos() {
        return empleadoRepository.findByActivoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener empleados por rol
     */
    public List<EmpleadoDTO> obtenerEmpleadosPorRol(RolEmpleado rol) {
        return empleadoRepository.findByRol(rol).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener empleados por departamento
     */
    public List<EmpleadoDTO> obtenerEmpleadosPorDepartamento(String departamento) {
        return empleadoRepository.findByDepartamento(departamento).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Contar empleados activos
     */
    public long contarEmpleadosActivos() {
        return empleadoRepository.countByActivoTrue();
    }

    /**
     * Verificar permisos del empleado
     */
    public boolean tienePermiso(String clerkUserId, String permiso) {
        Empleado empleado = empleadoRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        return empleado.getRol().tienePermiso(permiso);
    }

    /**
     * Convertir entidad a DTO
     */
    private EmpleadoDTO convertirADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setClerkUserId(empleado.getUsuario().getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setCorreo(empleado.getCorreo());
        dto.setRol(empleado.getRol());
        dto.setDepartamento(empleado.getDepartamento());
        dto.setFechaContratacion(empleado.getFechaContratacion());
        dto.setActivo(empleado.getActivo());
        dto.setTelefono(empleado.getTelefono());
        dto.setFechaCreacion(empleado.getFechaCreacion());
        dto.setFechaActualizacion(empleado.getFechaActualizacion());
        return dto;
    }
}