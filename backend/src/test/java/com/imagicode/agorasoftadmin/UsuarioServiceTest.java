package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.servicios.UsuarioService;
import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario("1", "Juan", "Pérez", "juan@test.com", "ADMIN", "ORG1");
        usuario.setActivo(true);
        usuario.setCreatedAt(LocalDateTime.now());
    }

    // ---------------------------------------------------------
    // 1. Obtener todos los usuarios
    // ---------------------------------------------------------

    @Test
    void obtenerUsuarios_retornaLista() {
        List<Usuario> usuarios = List.of(usuario);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }

    // ---------------------------------------------------------
    // 2. Guardar usuario
    // ---------------------------------------------------------

    @Test
    void guardarUsuario_guardaCorrectamente() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.guardarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    // ---------------------------------------------------------
    // 3. Obtener usuario por ID
    // ---------------------------------------------------------

    @Test
    void obtenerUsuarioPorId_existente() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId("1");

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository).findById("1");
    }

    @Test
    void obtenerUsuarioPorId_noExistente() {
        when(usuarioRepository.findById("2")).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.obtenerUsuarioPorId("2");

        assertNull(resultado);
        verify(usuarioRepository).findById("2");
    }

    // ---------------------------------------------------------
    // 4. Obtener usuario por correo
    // ---------------------------------------------------------

    @Test
    void obtenerUsuarioPorCorreo_existente() {
        when(usuarioRepository.findByCorreo("juan@test.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorCorreo("juan@test.com");

        assertTrue(resultado.isPresent());
        verify(usuarioRepository).findByCorreo("juan@test.com");
    }

    @Test
    void obtenerUsuarioPorCorreo_noExistente() {
        when(usuarioRepository.findByCorreo("otro@test.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorCorreo("otro@test.com");

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findByCorreo("otro@test.com");
    }

    // ---------------------------------------------------------
    // 5. Actualizar usuario
    // ---------------------------------------------------------

    @Test
    void actualizarUsuario_guardadoCorrectamente() {
        usuario.setNombre("Luis");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.actualizarUsuario(usuario);

        assertEquals("Luis", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    // ---------------------------------------------------------
    // 6. esEmpleado
    // ---------------------------------------------------------

    @Test
    void esEmpleado_true() {
        when(usuarioRepository.esEmpleado("123")).thenReturn(true);

        boolean resultado = usuarioService.esEmpleado("123");

        assertTrue(resultado);
        verify(usuarioRepository).esEmpleado("123");
    }

    @Test
    void esEmpleado_false() {
        when(usuarioRepository.esEmpleado("123")).thenReturn(false);

        boolean resultado = usuarioService.esEmpleado("123");

        assertFalse(resultado);
        verify(usuarioRepository).esEmpleado("123");
    }

    // ---------------------------------------------------------
    // 7. crearUsuario
    // ---------------------------------------------------------

    @Test
    void crearUsuario_correctamente() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.crearUsuario(usuario);

        assertNotNull(resultado);
        verify(usuarioRepository).save(usuario);
    }

    // ---------------------------------------------------------
    // 8. esRepresentantePlaza
    // ---------------------------------------------------------

    @Test
    void esRepresentantePlaza_true() {
        when(usuarioRepository.esRepresentantePlaza("123")).thenReturn(true);

        boolean resultado = usuarioService.esRepresentantePlaza("123");

        assertTrue(resultado);
        verify(usuarioRepository).esRepresentantePlaza("123");
    }

    @Test
    void esRepresentantePlaza_false() {
        when(usuarioRepository.esRepresentantePlaza("123")).thenReturn(false);

        boolean resultado = usuarioService.esRepresentantePlaza("123");

        assertFalse(resultado);
        verify(usuarioRepository).esRepresentantePlaza("123");
    }

    // ---------------------------------------------------------
    // 9. eliminarUsuario
    // ---------------------------------------------------------

    @Test
    void eliminarUsuario_eliminaCorrectamente() {
        doNothing().when(usuarioRepository).deleteById("1");

        usuarioService.eliminarUsuario("1");

        verify(usuarioRepository).deleteById("1");
    }
}
