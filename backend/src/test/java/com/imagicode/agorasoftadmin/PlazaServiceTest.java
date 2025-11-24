package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.dto.*;
import com.imagicode.agorasoftadmin.entidades.*;
import com.imagicode.agorasoftadmin.repositorios.*;
import com.imagicode.agorasoftadmin.servicios.ClerkService;
import com.imagicode.agorasoftadmin.servicios.PlazaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlazaServiceTest {

    @Mock private PlazaRepository plazaRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private SuscripcionRepository suscripcionRepository;
    @Mock private NotificacionRepository notificacionRepository;
    @Mock private ClerkService clerkService;

    @InjectMocks private PlazaService plazaService;

    @BeforeEach
    void setup() {
        // nothing global yet
    }

    @Test
    void crearPlaza_DeberiaGuardarCuandoRutNoExiste() {
        CrearPlazaDTO dto = new CrearPlazaDTO();
        dto.setRut("123");
        dto.setNombre("Plaza 1");
        dto.setDireccion("Calle 1");
        dto.setTelefono("300");
        dto.setEmailContacto("contacto@plaza.com");
        dto.setRepresentanteLegal("Rep");

        when(plazaRepository.existsByRut("123")).thenReturn(false);

        Plaza saved = new Plaza();
        saved.setId(1L);
        saved.setRut("123");
        saved.setNombre("Plaza 1");

        when(plazaRepository.save(any(Plaza.class))).thenReturn(saved);

        PlazaDTO result = plazaService.crearPlaza(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(plazaRepository).existsByRut("123");
        verify(plazaRepository).save(any(Plaza.class));
    }

    @Test
    void crearPlaza_CuandoRutExiste_DeberiaLanzar() {
        CrearPlazaDTO dto = new CrearPlazaDTO();
        dto.setRut("r1");
        when(plazaRepository.existsByRut("r1")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> plazaService.crearPlaza(dto));
        assertEquals("Ya existe una plaza con ese RUT", ex.getMessage());
        verify(plazaRepository).existsByRut("r1");
        verify(plazaRepository, never()).save(any());
    }

    @Test
    void obtenerPlazaPorId_CuandoExiste_RetornaDTO() {
        Plaza p = new Plaza();
        p.setId(5L);
        p.setNombre("P5");
        p.setRut("R5");
        p.setFechaRegistro(LocalDateTime.now());

        when(plazaRepository.findById(5L)).thenReturn(Optional.of(p));

        PlazaDTO dto = plazaService.obtenerPlazaPorId(5L);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        verify(plazaRepository).findById(5L);
    }

    @Test
    void obtenerPlazaPorId_NoExiste_Lanza() {
        when(plazaRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> plazaService.obtenerPlazaPorId(99L));
        assertEquals("Plaza no encontrada", ex.getMessage());
    }

    @Test
void aprobarPlaza_CaminoFeliz() {
    // preparar plaza pendiente
    Plaza plaza = new Plaza();
    plaza.setId(10L);
    plaza.setEstado(EstadoPlaza.PENDIENTE);
    plaza.setEmailContacto("rep@plaza.com");
    plaza.setNombre("Plaza10");
    plaza.setRepresentanteLegal("Rep10");

    when(plazaRepository.findById(10L)).thenReturn(Optional.of(plaza));

    // empleado con permisos APPROVE_PLAZAS
    Empleado empleado = new Empleado();
    empleado.setId(2L);
    empleado.setNombre("Admin");
    empleado.setApellido("A");
    empleado.setRol(RolEmpleado.ADMIN);
    Usuario uEmp = new Usuario();
    uEmp.setId("clerk_emp_2");
    empleado.setUsuario(uEmp);

    when(empleadoRepository.findByClerkUserId("clerk_emp_2")).thenReturn(Optional.of(empleado));

    // clerk service devuelve id
    when(clerkService.crearUsuarioRepresentantePlaza("rep@plaza.com", "Rep10"))
        .thenReturn("clerk_rep_100");

    // usuarioRepository.save guarda usuario
    Usuario usuarioGuardado = new Usuario();
    usuarioGuardado.setId("clerk_rep_100");
    usuarioGuardado.setNombre("Rep10");
    usuarioGuardado.setCorreo("rep@plaza.com");
    when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

    // plazaRepository.save devuelve plaza aprobado con datos completos
    Plaza saved = new Plaza();
    saved.setId(10L);
    saved.setUsuario(usuarioGuardado);
    saved.setEstado(EstadoPlaza.ACTIVA);
    saved.setEmailContacto("rep@plaza.com");          // <--- línea agregada
    saved.setNombre("Plaza10");                       // <--- línea agregada
    saved.setRepresentanteLegal("Rep10");             // <--- línea agregada
    when(plazaRepository.save(any(Plaza.class))).thenReturn(saved);

    // suscripcion & notificacion save
    when(suscripcionRepository.save(any())).thenReturn(null);
    when(notificacionRepository.save(any())).thenReturn(null);

    // DTO con plan/monto/metodo
    AprobarPlazaDTO dto = new AprobarPlazaDTO();
    dto.setPlanInicial("BASIC");
    dto.setMontoMensual(BigDecimal.valueOf(100.0));
    dto.setMetodoPago("CARD");

    PlazaDTO result = plazaService.aprobarPlaza(10L, "clerk_emp_2", dto);

    assertNotNull(result);
    assertEquals(10L, result.getId());
    assertEquals(EstadoPlaza.ACTIVA, saved.getEstado());

    verify(clerkService).crearUsuarioRepresentantePlaza("rep@plaza.com", "Rep10");
    verify(usuarioRepository).save(any(Usuario.class));
    verify(plazaRepository).save(any(Plaza.class));
    verify(suscripcionRepository).save(any());
    verify(notificacionRepository).save(any());

    // Capturamos los argumentos usados en enviarCorreoAprobacionPlaza
    ArgumentCaptor<String> correoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> nombreCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> representanteCaptor = ArgumentCaptor.forClass(String.class);

    verify(clerkService).enviarCorreoAprobacionPlaza(
        correoCaptor.capture(),
        nombreCaptor.capture(),
        representanteCaptor.capture()
    );

    assertEquals("rep@plaza.com", correoCaptor.getValue());
    assertEquals("Plaza10", nombreCaptor.getValue());
    assertEquals("Rep10", representanteCaptor.getValue());
}


    @Test
    void suspenderPlaza_Error_SiNoActiva() {
        Plaza plaza = new Plaza();
        plaza.setId(20L);
        plaza.setEstado(EstadoPlaza.PENDIENTE);
        when(plazaRepository.findById(20L)).thenReturn(Optional.of(plaza));

        SuspenderPlazaDTO dto = new SuspenderPlazaDTO();
        dto.setMotivo("Motivo");

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> plazaService.suspenderPlaza(20L, "c", dto));

        assertEquals("Solo se pueden suspender plazas en estado ACTIVA", ex.getMessage());
    }


    @Test
    void activarPlaza_CaminoFeliz_DeberiaActivarYLlamarClerk() {
        Plaza plaza = new Plaza();
        plaza.setId(30L);
        plaza.setEstado(EstadoPlaza.SUSPENDIDA);

        Usuario usuario = new Usuario();
        usuario.setId("clerk_rep");
        plaza.setUsuario(usuario);

        when(plazaRepository.findById(30L)).thenReturn(Optional.of(plaza));

        Empleado empleado = new Empleado();
        empleado.setId(5L);
        empleado.setRol(RolEmpleado.ADMIN);
        when(empleadoRepository.findByClerkUserId("clerk_admin")).thenReturn(Optional.of(empleado));

        Plaza saved = new Plaza();
        saved.setId(30L);
        saved.setEstado(EstadoPlaza.ACTIVA);
        saved.setUsuario(usuario);

        when(plazaRepository.save(any(Plaza.class))).thenReturn(saved);

        PlazaDTO result = plazaService.activarPlaza(30L, "clerk_admin");

        assertNotNull(result);
        assertEquals(30L, result.getId());
        verify(clerkService).activarUsuario("clerk_rep");
        verify(notificacionRepository).save(any());
    }
}
