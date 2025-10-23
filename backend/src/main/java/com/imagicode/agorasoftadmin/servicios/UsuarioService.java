package com.imagicode.agorasoftadmin.servicios;

import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
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

}
