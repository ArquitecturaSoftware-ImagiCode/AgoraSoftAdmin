package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.*;
import com.imagicode.agorasoftadmin.entidades.*;
import com.imagicode.agorasoftadmin.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlazaService {

    @Autowired
    private PlazaRepository plazaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SuscripcionRepository suscripcionRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ClerkService clerkService;

    /**
     * Crear nueva plaza (registro desde frontend público)
     */
    @Transactional
    public PlazaDTO crearPlaza(CrearPlazaDTO dto) {
        // Verificar que el RUT no exista
        if (plazaRepository.existsByRut(dto.getRut())) {
            throw new RuntimeException("Ya existe una plaza con ese RUT");
        }

        Plaza plaza = new Plaza();
        plaza.setNombre(dto.getNombre());
        plaza.setRut(dto.getRut());
        plaza.setDireccion(dto.getDireccion());
        plaza.setTelefono(dto.getTelefono());
        plaza.setEmailContacto(dto.getEmailContacto());
        plaza.setRepresentanteLegal(dto.getRepresentanteLegal());
        plaza.setEstado(EstadoPlaza.PENDIENTE);

        plaza = plazaRepository.save(plaza);

        // Enviar correo de confirmación de recepción al contacto de la plaza
        clerkService.enviarCorreoRegistroPlazaRecibido(
                plaza.getEmailContacto(),
                plaza.getNombre(),
                plaza.getRepresentanteLegal());

        return convertirADTO(plaza);
    }

    /**
     * Obtener todas las plazas
     */
    public List<PlazaDTO> obtenerTodasLasPlazas() {
        return plazaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener plaza por ID
     */
    public PlazaDTO obtenerPlazaPorId(Long id) {
        Plaza plaza = plazaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));
        return convertirADTO(plaza);
    }

    /**
     * Obtener plazas pendientes de aprobación
     */
    public List<PlazaDTO> obtenerPlazasPendientes() {
        return plazaRepository.findPlazasPendientes().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener plazas por estado
     */
    public List<PlazaDTO> obtenerPlazasPorEstado(EstadoPlaza estado) {
        return plazaRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Buscar plazas
     */
    public List<PlazaDTO> buscarPlazas(String searchTerm) {
        return plazaRepository.searchPlazas(searchTerm).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Aprobar plaza
     */
    @Transactional
    public PlazaDTO aprobarPlaza(Long plazaId, String empleadoClerkUserId, AprobarPlazaDTO dto) {
        Plaza plaza = plazaRepository.findById(plazaId)
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));

        if (plaza.getEstado() != EstadoPlaza.PENDIENTE) {
            throw new RuntimeException("Solo se pueden aprobar plazas en estado PENDIENTE");
        }

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("APPROVE_PLAZAS")) {
            throw new RuntimeException("No tiene permisos para aprobar plazas");
        }

        // Crear usuario en Clerk para el representante
        String clerkUserId = clerkService.crearUsuarioRepresentantePlaza(
                plaza.getEmailContacto(),
                plaza.getRepresentanteLegal());

        // Crear entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setId(clerkUserId);
        usuario.setNombre(plaza.getRepresentanteLegal());
        usuario.setCorreo(plaza.getEmailContacto());
        usuario.setRol("REPRESENTANTE_PLAZA");
        usuario.setOrganizacion(plaza.getNombre());
        usuario = usuarioRepository.save(usuario);

        // Aprobar plaza
        plaza.setUsuario(usuario);
        plaza.aprobar(empleado);
        plaza = plazaRepository.save(plaza);

        // Crear suscripción inicial
        Suscripcion suscripcion = new Suscripcion(
                plaza,
                dto.getPlanInicial(),
                dto.getMontoMensual(),
                dto.getMetodoPago(),
                empleado);
        suscripcionRepository.save(suscripcion);

        // Enviar notificación a la plaza
        Notificacion notificacion = new Notificacion(
                plaza,
                "Plaza Aprobada",
                "Su plaza ha sido aprobada. Ya puede acceder al sistema con sus credenciales.",
                TipoNotificacion.INFORMATIVA,
                empleado);
        notificacionRepository.save(notificacion);

        // Enviar correo con credenciales
        clerkService.enviarCorreoAprobacionPlaza(
                plaza.getEmailContacto(),
                plaza.getNombre(),
                plaza.getRepresentanteLegal());

        return convertirADTO(plaza);
    }

    /**
     * Rechazar plaza
     */
    @Transactional
    public PlazaDTO rechazarPlaza(Long plazaId, String empleadoClerkUserId, RechazarPlazaDTO dto) {
        Plaza plaza = plazaRepository.findById(plazaId)
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));

        if (plaza.getEstado() != EstadoPlaza.PENDIENTE) {
            throw new RuntimeException("Solo se pueden rechazar plazas en estado PENDIENTE");
        }

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("APPROVE_PLAZAS")) {
            throw new RuntimeException("No tiene permisos para rechazar plazas");
        }

        plaza.rechazar(empleado, dto.getMotivo());
        plaza = plazaRepository.save(plaza);

        // Enviar correo de rechazo
        clerkService.enviarCorreoRechazoPlaza(
                plaza.getEmailContacto(),
                plaza.getNombre(),
                dto.getMotivo());

        return convertirADTO(plaza);
    }

    /**
     * Suspender plaza
     */
    @Transactional
    public PlazaDTO suspenderPlaza(Long plazaId, String empleadoClerkUserId, SuspenderPlazaDTO dto) {
        Plaza plaza = plazaRepository.findById(plazaId)
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));

        if (plaza.getEstado() != EstadoPlaza.ACTIVA) {
            throw new RuntimeException("Solo se pueden suspender plazas en estado ACTIVA");
        }

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("MANAGE_PLAZAS")) {
            throw new RuntimeException("No tiene permisos para suspender plazas");
        }

        plaza.suspender(empleado, dto.getMotivo());
        plaza = plazaRepository.save(plaza);

        // Bloquear acceso del representante en Clerk
        if (plaza.getUsuario() != null) {
            clerkService.desactivarUsuario(plaza.getUsuario().getId());
        }

        // Enviar notificación
        Notificacion notificacion = new Notificacion(
                plaza,
                "Plaza Suspendida",
                "Su plaza ha sido suspendida. Motivo: " + dto.getMotivo(),
                TipoNotificacion.URGENTE,
                empleado);
        notificacionRepository.save(notificacion);

        return convertirADTO(plaza);
    }

    /**
     * Activar plaza suspendida
     */
    @Transactional
    public PlazaDTO activarPlaza(Long plazaId, String empleadoClerkUserId) {
        Plaza plaza = plazaRepository.findById(plazaId)
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));

        if (plaza.getEstado() != EstadoPlaza.SUSPENDIDA) {
            throw new RuntimeException("Solo se pueden activar plazas en estado SUSPENDIDA");
        }

        Empleado empleado = empleadoRepository.findByClerkUserId(empleadoClerkUserId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Verificar permisos
        if (!empleado.getRol().tienePermiso("MANAGE_PLAZAS")) {
            throw new RuntimeException("No tiene permisos para activar plazas");
        }

        plaza.activar();
        plaza = plazaRepository.save(plaza);

        // Restaurar acceso del representante en Clerk
        if (plaza.getUsuario() != null) {
            clerkService.activarUsuario(plaza.getUsuario().getId());
        }

        // Enviar notificación
        Notificacion notificacion = new Notificacion(
                plaza,
                "Plaza Reactivada",
                "Su plaza ha sido reactivada. Ya puede acceder nuevamente al sistema.",
                TipoNotificacion.INFORMATIVA,
                empleado);
        notificacionRepository.save(notificacion);

        return convertirADTO(plaza);
    }

    /**
     * Contar plazas por estado
     */
    public long contarPlazasPorEstado(EstadoPlaza estado) {
        return plazaRepository.countByEstado(estado);
    }

    /**
     * Obtener plazas con pagos vencidos
     */
    public List<PlazaDTO> obtenerPlazasConPagosVencidos() {
        List<Suscripcion> suscripcionesVencidas = suscripcionRepository
                .findSuscripcionesVencidas(LocalDate.now());

        return suscripcionesVencidas.stream()
                .map(s -> convertirADTO(s.getPlaza()))
                .collect(Collectors.toList());
    }

    /**
     * Convertir entidad a DTO
     */
    private PlazaDTO convertirADTO(Plaza plaza) {
        PlazaDTO dto = new PlazaDTO();
        dto.setId(plaza.getId());
        dto.setNombre(plaza.getNombre());
        dto.setRut(plaza.getRut());
        dto.setDireccion(plaza.getDireccion());
        dto.setTelefono(plaza.getTelefono());
        dto.setEmailContacto(plaza.getEmailContacto());
        dto.setRepresentanteLegal(plaza.getRepresentanteLegal());
        dto.setFechaRegistro(plaza.getFechaRegistro());
        dto.setEstado(plaza.getEstado());
        dto.setFechaActualizacion(plaza.getFechaActualizacion());

        if (plaza.getUsuario() != null) {
            dto.setClerkUserId(plaza.getUsuario().getId());
        }

        if (plaza.getAprobadoPor() != null) {
            dto.setAprobadoPorId(plaza.getAprobadoPor().getId());
            dto.setAprobadoPorNombre(plaza.getAprobadoPor().getNombreCompleto());
            dto.setFechaAprobacion(plaza.getFechaAprobacion());
        }

        if (plaza.getRechazadoPor() != null) {
            dto.setRechazadoPorId(plaza.getRechazadoPor().getId());
            dto.setRechazadoPorNombre(plaza.getRechazadoPor().getNombreCompleto());
            dto.setFechaRechazo(plaza.getFechaRechazo());
            dto.setMotivoRechazo(plaza.getMotivoRechazo());
        }

        if (plaza.getSuspendidoPor() != null) {
            dto.setSuspendidoPorId(plaza.getSuspendidoPor().getId());
            dto.setSuspendidoPorNombre(plaza.getSuspendidoPor().getNombreCompleto());
            dto.setFechaSuspension(plaza.getFechaSuspension());
            dto.setMotivoSuspension(plaza.getMotivoSuspension());
        }

        // Estadísticas
        dto.setCantidadModulosActivos((int) plaza.getModulosContratados().stream()
                .filter(pm -> pm.getEstado() == EstadoModulo.ACTIVO)
                .count());

        if (plaza.getSuscripcion() != null) {
            dto.setPlanSuscripcion(plaza.getSuscripcion().getPlanActual());
            dto.setEstadoPago(plaza.getSuscripcion().getEstadoPago().name());
        }

        return dto;
    }
}