package com.imagicode.agorasoftadmin.servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

/**
 * Capa de aplicación/servicio para usuarios.
 * Publica eventos de dominio tras commit para disparar notificaciones.
 * Delega notificaciones administrativas a AdminNotifierService.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher events;
    private final AdminNotifierService adminNotifier;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          ApplicationEventPublisher events,
                          AdminNotifierService adminNotifier) {
        this.usuarioRepository = usuarioRepository;
        this.events = events;
        this.adminNotifier = adminNotifier;
    }

    /**
     * Crea un usuario sin password cruda (flujo normal con Clerk o externo).
     */
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        Usuario guardado = usuarioRepository.save(usuario);

        // Publica evento para listeners (correo de bienvenida, etc.)
        // El listener verifica notify.user-registration.enabled
        events.publishEvent(new UserRegisteredEvent(guardado, null));

        // Notifica al módulo administrativo de forma defensiva (evita NPE)
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", guardado.getId());
        payload.put("correo", guardado.getCorreo());
        payload.put("nombre", guardado.getNombre());
        payload.put("apellido", guardado.getApellido());
        payload.put("estado", guardado.getEstado() != null ? guardado.getEstado() : "PENDIENTE");
        payload.put("rol", guardado.getRol());
        adminNotifier.notifyNuevoRegistro(payload);

        return guardado;
    }

    /**
     * Variante para casos donde se necesita incluir la password en texto plano
     * (solo para desarrollo/testing o correos de credenciales iniciales).
     */
    @Transactional
    public Usuario crearUsuario(Usuario usuario, String rawPasswordOptional) {
        Usuario guardado = usuarioRepository.save(usuario);

        // Publica evento con password opcional
        events.publishEvent(new UserRegisteredEvent(guardado, rawPasswordOptional));

        // Notifica al módulo administrativo
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", guardado.getId());
        payload.put("correo", guardado.getCorreo());
        payload.put("nombre", guardado.getNombre());
        payload.put("apellido", guardado.getApellido());
        payload.put("estado", guardado.getEstado() != null ? guardado.getEstado() : "PENDIENTE");
        payload.put("rol", guardado.getRol());
        adminNotifier.notifyNuevoRegistro(payload);

        return guardado;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean esEmpleado(String clerkUserId) {
        return usuarioRepository.esEmpleado(clerkUserId);
    }

    public boolean esRepresentantePlaza(String clerkUserId) {
        return usuarioRepository.esRepresentantePlaza(clerkUserId);
    }

    public void eliminarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerUsuariosPorOrganizacion(String organizacion) {
        return usuarioRepository.findByOrganizacion(organizacion);
    }

    /**
     * Notifica a otro módulo (por ejemplo, administrativo) sobre un nuevo registro.
     */
    private void notificarNuevoRegistro(Usuario usuario) {
        try {
            String url = "http://localhost:8082/api/validaciones/nuevo-registro"; // URL del módulo administrativo

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = Map.of(
                    "id", usuario.getId(),
                    "correo", usuario.getCorreo(),
                    "nombre", usuario.getNombre(),
                    "apellido", usuario.getApellido(),
                    "estado", usuario.getEstado(),
                    "rol", usuario.getRol()
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("Evento de nuevo registro enviado al módulo administrativo: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Error al notificar nuevo registro: " + e.getMessage());
        }
    }
}
