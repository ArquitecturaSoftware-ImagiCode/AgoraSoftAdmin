package com.imagicode.agorasoftadmin.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;

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

    /**
     * Crea usuario y dispara evento de "usuario registrado".
     * 
     * @param usuario entidad a persistir (la lógica de password hashing debe
     *                hacerse aquí).
     */
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        // Guarda usuario (la lógica de hashing de password ya se hizo en el
        // controlador) y devuelve el usuario guardado (para el id, que siempre
        // corresponde).
        Usuario guardado = repo.save(usuario);

        // Evento para listeners (correo, etc.). No incluye password cruda.
        events.publishEvent(new UserRegisteredEvent(guardado, null));

        // Notificación administrativa (opcional, controlada por property/ENV). No
        // bloqueante.
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