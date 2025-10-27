package com.imagicode.agorasoftadmin.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

/**
 * Capa de aplicación/servicio para usuarios.
 * Publica eventos de dominio tras commit para disparar notificaciones.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final ApplicationEventPublisher events;
    private final AdminNotifierService adminNotifier;

    public UsuarioService(UsuarioRepository repo,
            ApplicationEventPublisher events,
            AdminNotifierService adminNotifier) {
        this.repo = repo;
        this.events = events;
        this.adminNotifier = adminNotifier;
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        Usuario guardado = repo.save(usuario);

        // Se publica el evento, pero el listener no enviará correo si
        // notify.user-registration.enabled=false
        events.publishEvent(new UserRegisteredEvent(guardado, null));

        // Map.of throws NPE if any value is null. Build payload defensively.
        java.util.Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("id", guardado.getId());
        payload.put("correo", guardado.getCorreo());
        payload.put("nombre", guardado.getNombre());
        payload.put("apellido", guardado.getApellido());
        payload.put("estado", guardado.getEstado() != null ? guardado.getEstado() : "PENDIENTE");
        payload.put("rol", guardado.getRol());
        adminNotifier.notifyNuevoRegistro(payload);

        return guardado;
    }

    // Variante si en algún flujo se requiere incluir la password cruda (solo dev).
    @Transactional
    public Usuario crearUsuario(Usuario usuario, String rawPasswordOptional) {
        Usuario guardado = repo.save(usuario);
        events.publishEvent(new UserRegisteredEvent(guardado, rawPasswordOptional));
        Map<String, Object> payload = Map.of(
                "id", guardado.getId(),
                "correo", guardado.getCorreo(),
                "nombre", guardado.getNombre(),
                "apellido", guardado.getApellido(),
                "estado", guardado.getEstado(),
                "rol", guardado.getRol());

        adminNotifier.notifyNuevoRegistro(payload);
        return guardado;
    }

    public List<Usuario> obtenerUsuarios() {
        return repo.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return repo.findById(id).orElse(null);
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return repo.findByCorreo(correo);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    public boolean esEmpleado(String clerkUserId) {
        return repo.esEmpleado(clerkUserId);
    }

    public boolean esRepresentantePlaza(String clerkUserId) {
        return repo.esRepresentantePlaza(clerkUserId);
    }

    public void eliminarUsuario(String id) {
        repo.deleteById(id);
    }
}