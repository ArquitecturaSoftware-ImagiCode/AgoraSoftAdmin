package com.imagicode.agorasoftadmin.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.servicios.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ResponseEntity<Usuario> register(@RequestBody Usuario payload) {
        // No exponemos password cruda por defecto (seguro). Si se requiere en dev,
        // usar la sobrecarga con rawPassword y habilitar notify.include-raw-password.
        Usuario creador = usuarioService.crearUsuario(payload);
        return ResponseEntity.status(201).body(creador);
    }

    // GET: traer todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    // GET: traer usuario por id
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable String id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // POST: crear usuario
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        System.out.println("Creando usuario: " + usuario);
        return usuarioService.crearUsuario(usuario);
    }

    // DELETE: eliminar usuario
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
    }
}
