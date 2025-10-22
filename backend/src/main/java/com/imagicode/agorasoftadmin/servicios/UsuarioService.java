package com.imagicode.agorasoftadmin.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.notificaciones.events.UserRegisteredEvent;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    private final UsuarioRepository repo;
    private final ApplicationEventPublisher events;
    private final AdminNotifierService adminNotifier;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioRepository repo,
            ApplicationEventPublisher events, AdminNotifierService adminNotifier) {
        this.usuarioRepository = usuarioRepository;
        this.repo = repo;
        this.events = events;
        this.adminNotifier = adminNotifier;
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario, String rawPasswordOptional) {
        // validar / hashear password aquí si aplica
        Usuario guardado = usuarioRepository.save(usuario);

        // publicar evento para notificaciones por email (listener actual)
        events.publishEvent(new UserRegisteredEvent(guardado, rawPasswordOptional));

        // notificar módulo administrativo de forma asíncrona
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

    // Sobrecarga si tu controlador actual no pasa password explícito
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        Usuario guardado = repo.save(usuario);
        events.publishEvent(new UserRegisteredEvent(guardado, null));
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

}
