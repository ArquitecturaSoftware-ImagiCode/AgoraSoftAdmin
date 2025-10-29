package com.imagicode.agorasoftadmin.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
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