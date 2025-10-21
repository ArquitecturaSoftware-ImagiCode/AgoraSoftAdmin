package com.imagicode.agorasoftadmin.controladores;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.servicios.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/clerk")
@CrossOrigin(origins = "*")
public class ClerkUserController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public Object getUsuario(HttpServletRequest request) {
        String userId = (String) request.getAttribute("clerkUserId");
        if (userId == null) {
            return Map.of("error", "No Clerk userId en el request");
        }
        Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
        if (usuario != null) {
            return usuario;
        } else {
            return Map.of("error", "Usuario no encontrado en la base de datos");
        }
    }

    @PostMapping("/usuarios")
    public Object crearUsuarioDesdeLanding(@RequestBody Usuario nuevoUsuario) {
        try {
            // Estado inicial: PENDIENTE
            nuevoUsuario.setEstado("PENDIENTE");
            nuevoUsuario.setActivo(false);
            nuevoUsuario.setCreatedAt(LocalDateTime.now());

            Usuario guardado = usuarioService.crearUsuario(nuevoUsuario);
            return Map.of(
                    "mensaje", "Usuario registrado con estado PENDIENTE",
                    "usuario", guardado);
        } catch (Exception e) {
            return Map.of("error", "Error al registrar el usuario: " + e.getMessage());
        }
    }
}
