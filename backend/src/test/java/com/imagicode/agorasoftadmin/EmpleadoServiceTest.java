package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.servicios.ClerkService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.imagicode.agorasoftadmin.entidades.Empleado;
import com.imagicode.agorasoftadmin.entidades.RolEmpleado;
import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.dto.ActualizarEmpleadoDTO;
import com.imagicode.agorasoftadmin.dto.CrearEmpleadoDTO;
import com.imagicode.agorasoftadmin.dto.EmpleadoDTO;
import com.imagicode.agorasoftadmin.repositorios.EmpleadoRepository;
import com.imagicode.agorasoftadmin.repositorios.UsuarioRepository;
import com.imagicode.agorasoftadmin.servicios.EmpleadoService;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock private EmpleadoRepository empleadoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ClerkService clerkService;
    @InjectMocks private EmpleadoService empleadoService;
    private RolEmpleado roles;
    private Usuario usuario;
    private Empleado empleado;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("1", "Juan", "Pérez", "juan@test.com", "ADMIN", "ORG1");
        usuario.setActivo(true);
        usuario.setCreatedAt(LocalDateTime.now());

        empleado = new Empleado(usuario,"Juan","Pérez","juan@test.com",roles.ADMIN,"TI");
    }

    @Test
    void crearEmpleado_exitoso(){
        CrearEmpleadoDTO dto = new CrearEmpleadoDTO();
        dto.setCorreo("test@correo.com");
        dto.setNombre("Juan");
        dto.setApellido("Prueba");
        dto.setRol(roles.ADMIN);
        dto.setDepartamento(null);
        dto.setTelefono("999999");

        when(empleadoRepository.existsByCorreo("test@correo.com")).thenReturn(false);
        when(clerkService.crearUsuarioEmpleado(dto.getCorreo(), dto.getNombre(), dto.getApellido()))
        .thenReturn("clerk123");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId("clerk123");
        usuarioMock.setNombre(dto.getNombre());
        usuarioMock.setApellido(dto.getApellido());
        usuarioMock.setCorreo(dto.getCorreo());
        usuarioMock.setRol("Empleado");
        usuarioMock.setOrganizacion(null);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        Empleado empleadoMock  = new Empleado();
        empleadoMock.setUsuario(usuarioMock);
        empleadoMock.setNombre(dto.getNombre());
        empleadoMock.setApellido(dto.getApellido());
        empleadoMock.setCorreo(dto.getCorreo());
        empleadoMock.setRol(dto.getRol());
        empleadoMock.setDepartamento(dto.getDepartamento());
        empleadoMock.setTelefono(dto.getTelefono());
        empleadoMock.setActivo(true);

        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoMock);

        EmpleadoDTO resultado = empleadoService.crearEmpleado(dto);

        assertNotNull(resultado);
        assertEquals("test@correo.com",resultado.getCorreo());

        verify(clerkService).crearUsuarioEmpleado("test@correo.com", "Juan", "Prueba");
        verify(clerkService).enviarCorreoBienvenida("test@correo.com", "Juan");

        verify(usuarioRepository).save(any(Usuario.class));
        verify(empleadoRepository).save(any(Empleado.class));
    }

    @Test void CrearEmpleado_CorreoExistente_LanzaError(){
        CrearEmpleadoDTO dto = new CrearEmpleadoDTO();

        dto.setCorreo("repetido@correo.com");
        dto.setNombre("Juan");
        dto.setApellido("Prueba");
        dto.setRol(roles.ADMIN);

        when(empleadoRepository.existsByCorreo(dto.getCorreo()))
        .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            empleadoService.crearEmpleado(dto);
        });

        verify(clerkService, never()).crearUsuarioEmpleado(any(),any(),any());
        verify(usuarioRepository, never()).save(any());
        verify(empleadoRepository, never()).save(any());
    }
    @Test
    void obtenerEmpleados_RetornarLista(){
        List<Empleado> empleados = List.of(empleado);
        when(empleadoRepository.findAll()).thenReturn(empleados);
        List<EmpleadoDTO> resultado = empleadoService.obtenerTodosLosEmpleados();
        assertEquals(1, resultado.size());
        verify(empleadoRepository).findAll();
    }
    
    @Test
    void obtenerEmpleadoPorId_existente(){
        long id = 1;
        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        EmpleadoDTO resultado = empleadoService.obtenerEmpleadoPorId(id);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(empleadoRepository).findById(id);
    }

    @Test
    void obtenerEmpleadoPorId_Noexistente(){
        long id = 2;
        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()->{
            empleadoService.obtenerEmpleadoPorId(id);
        });
        verify(empleadoRepository).findById(id);
    }

    @Test
    void obtenerEmpleadoPorClerkUserId_existente(){
        when(empleadoRepository.findByClerkUserId(empleado.getUsuario().getId())).thenReturn(Optional.of(empleado));

        EmpleadoDTO resultado = empleadoService.obtenerEmpleadoPorClerkUserId(empleado.getUsuario().getId());

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(empleadoRepository).findByClerkUserId(empleado.getUsuario().getId());
    }

    @Test
    void obtenerEmpleadoPorClerkUserId_NOexistente(){
        when(empleadoRepository.findByClerkUserId(empleado.getUsuario().getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()->{
            empleadoService.obtenerEmpleadoPorClerkUserId(empleado.getUsuario().getId());
        });
        verify(empleadoRepository).findByClerkUserId(empleado.getUsuario().getId());
    }

    @Test
    void actualizarEmpleado_exitoso() {
        Long id = 1L;

        // --- Empleado existente ---
        Empleado empleadoExistente = new Empleado();
        empleadoExistente.setId(id);
        empleadoExistente.setNombre("NombreAntiguo");
        empleadoExistente.setApellido("ApellidoAntiguo");
        empleadoExistente.setCorreo("correo@correo.com");
        empleadoExistente.setTelefono("111111");
        empleadoExistente.setDepartamento("Ventas");
        empleadoExistente.setActivo(true);

        // AGREGAR USUARIO PARA EVITAR NPE
        Usuario usuario = new Usuario();
        usuario.setId("10");
        empleadoExistente.setUsuario(usuario);

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleadoExistente));

        // --- Valores nuevos ---
        ActualizarEmpleadoDTO dto = new ActualizarEmpleadoDTO();
        dto.setNombre("NombreNuevo");
        dto.setApellido("ApellidoNuevo");
        dto.setTelefono("222222");
        dto.setActivo(false);

        // Mock del save
        when(empleadoRepository.save(any(Empleado.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // --- Ejecutar ---
        EmpleadoDTO result = empleadoService.actualizarEmpleado(id, dto);

        // --- Verificaciones ---
        assertNotNull(result);
        assertEquals("NombreNuevo", result.getNombre());
        assertEquals("ApellidoNuevo", result.getApellido());
        assertEquals("222222", result.getTelefono());
        assertFalse(result.getActivo());
        assertEquals("10", result.getClerkUserId()); // verificación para el usuario

        verify(empleadoRepository).findById(id);
        verify(empleadoRepository).save(any(Empleado.class));
    }
    @Test
    void actualizarEmpleado_noExiste_lanzaError() {
        Long id = 99L;
        ActualizarEmpleadoDTO dto = new ActualizarEmpleadoDTO();

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            empleadoService.actualizarEmpleado(id, dto);
        });

        verify(empleadoRepository).findById(id);
        verify(empleadoRepository, never()).save(any());
    }

    @Test
    void desactivarEmpleado_exitoso() {
        Long id = 1L;

        // --- Empleado existente ---
        Usuario usuario = new Usuario();
        usuario.setId("10");

        Empleado empleadoExistente = new Empleado();
        empleadoExistente.setId(id);
        empleadoExistente.setUsuario(usuario);
        empleadoExistente.setActivo(true); // Estado inicial

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleadoExistente));
        when(empleadoRepository.save(any(Empleado.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // --- Ejecutar ---
        EmpleadoDTO resultado = empleadoService.desactivarEmpleado(id);

        // --- Verificaciones ---
        assertNotNull(resultado);
        assertFalse(resultado.getActivo());  // Debe estar desactivado

        verify(empleadoRepository).findById(id);
        verify(empleadoRepository).save(any(Empleado.class));
        verify(clerkService).desactivarUsuario("10"); // Verifica llamada al Clerk
    }
    @Test
    void desactivarEmpleado_noExiste() {
        Long id = 99L;

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> empleadoService.desactivarEmpleado(id));

        verify(empleadoRepository).findById(id);
        verify(clerkService, never()).desactivarUsuario(anyString());
    }
    @Test
    void activarEmpleado_exitoso() {
        Long id = 1L;

        Usuario usuario = new Usuario();
        usuario.setId("10");

        Empleado empleadoExistente = new Empleado();
        empleadoExistente.setId(id);
        empleadoExistente.setUsuario(usuario);
        empleadoExistente.setActivo(false); // Estado inicial desactivado

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleadoExistente));
        when(empleadoRepository.save(any(Empleado.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // --- Ejecutar ---
        EmpleadoDTO resultado = empleadoService.activarEmpleado(id);

        // --- Verificaciones ---
        assertNotNull(resultado);
        assertTrue(resultado.getActivo());  // Debe estar activado

        verify(empleadoRepository).findById(id);
        verify(empleadoRepository).save(any(Empleado.class));
        verify(clerkService).activarUsuario("10");
    }
    @Test
    void activarEmpleado_noExiste() {
        Long id = 99L;

        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> empleadoService.activarEmpleado(id));

        verify(empleadoRepository).findById(id);
        verify(clerkService, never()).activarUsuario(anyString());
    }
    @Test
    void buscarEmpleados_retornaLista() {
        String searchTerm = "Juan";

        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan");
        Usuario usuario = new Usuario();
        usuario.setId("10");
        empleado.setUsuario(usuario);

        when(empleadoRepository.searchEmpleados(searchTerm))
            .thenReturn(List.of(empleado));

        List<EmpleadoDTO> resultado = empleadoService.buscarEmpleados(searchTerm);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());

        verify(empleadoRepository).searchEmpleados(searchTerm);
    }

    @Test
    void obtenerEmpleadosActivos_retornaLista() {
        Empleado emp = new Empleado();
        emp.setId(1L);
        emp.setActivo(true);
        Usuario usuario = new Usuario();
        usuario.setId("10");
        emp.setUsuario(usuario);

        when(empleadoRepository.findByActivoTrue())
            .thenReturn(List.of(emp));

        List<EmpleadoDTO> resultado = empleadoService.obtenerEmpleadosActivos();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());

        verify(empleadoRepository).findByActivoTrue();
    }
    @Test
    void obtenerEmpleadosPorRol_retornaLista() {
        RolEmpleado rol = RolEmpleado.ADMIN;

        Empleado emp = new Empleado();
        emp.setId(1L);
        emp.setRol(rol);
        Usuario usuario = new Usuario();
        usuario.setId("10");
        emp.setUsuario(usuario);

        when(empleadoRepository.findByRol(rol))
            .thenReturn(List.of(emp));

        List<EmpleadoDTO> resultado = empleadoService.obtenerEmpleadosPorRol(rol);

        assertEquals(1, resultado.size());
        assertEquals(rol, resultado.get(0).getRol());

        verify(empleadoRepository).findByRol(rol);
    }

    @Test
    void contarEmpleadosActivos_correcto() {
        when(empleadoRepository.countByActivoTrue()).thenReturn(5L);

        long resultado = empleadoService.contarEmpleadosActivos();

        assertEquals(5L, resultado);
        verify(empleadoRepository).countByActivoTrue();
    }

    @Test
    void tienePermiso_permisoCorrecto() {
        String clerkId = "10";
        String permiso = "CREAR_USUARIO";

        Empleado emp = mock(Empleado.class);
        RolEmpleado rolMock = mock(RolEmpleado.class);

        when(empleadoRepository.findByClerkUserId(clerkId))
            .thenReturn(Optional.of(emp));

        when(emp.getRol()).thenReturn(rolMock);
        when(rolMock.tienePermiso(permiso)).thenReturn(true);

        boolean resultado = empleadoService.tienePermiso(clerkId, permiso);

        assertTrue(resultado);
        verify(empleadoRepository).findByClerkUserId(clerkId);
        verify(rolMock).tienePermiso(permiso);
    }

    @Test
    void tienePermiso_empleadoNoExiste_lanzaExcepcion() {
        String clerkId = "20";

        when(empleadoRepository.findByClerkUserId(clerkId))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> empleadoService.tienePermiso(clerkId, "X"));

        verify(empleadoRepository).findByClerkUserId(clerkId);
    }
}
