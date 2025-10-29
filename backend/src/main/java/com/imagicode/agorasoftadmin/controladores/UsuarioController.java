    package com.imagicode.agorasoftadmin.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.servicios.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
