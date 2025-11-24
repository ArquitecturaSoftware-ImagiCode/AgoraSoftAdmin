package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.dto.CrearModuloDTO;
import com.imagicode.agorasoftadmin.dto.ModuloDTO;
import com.imagicode.agorasoftadmin.entidades.Modulo;
import com.imagicode.agorasoftadmin.repositorios.ModuloRepository;
import com.imagicode.agorasoftadmin.servicios.ModuloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuloServiceTest {

    @Mock private ModuloRepository moduloRepository;
    @InjectMocks private ModuloService moduloService;

    private CrearModuloDTO crearDto;

    @BeforeEach
    void setUp() {
        crearDto = new CrearModuloDTO();
        crearDto.setNombre("Modulo A");
        crearDto.setDescripcion("Descripción");
        crearDto.setIcono("icono.png");
        crearDto.setPrecioMensual(BigDecimal.valueOf(29.99));
        // CrearModuloDTO tiene getter getActivo; establece por constructor o directamente si existe setter
        // Si tu DTO no tiene setActivo, usa constructor en tu código de producción; aquí asumimos getter getActivo está disponible.
    }

    @Test
    void crearModulo_DeberiaCrearCuandoNoExisteNombre() {
        when(moduloRepository.existsByNombre("Modulo A")).thenReturn(false);

        Modulo saved = new Modulo();
        saved.setId(10L);
        saved.setNombre("Modulo A");
        saved.setDescripcion("Descripción");
        saved.setIcono("icono.png");
        saved.setPrecioMensual(BigDecimal.valueOf(29.99));
        saved.setActivo(true);

        when(moduloRepository.save(any(Modulo.class))).thenReturn(saved);

        ModuloDTO result = moduloService.crearModulo(crearDto, "anyClerkUserId");

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Modulo A", result.getNombre());

        verify(moduloRepository).existsByNombre("Modulo A");
        verify(moduloRepository).save(any(Modulo.class));
    }

    @Test
    void crearModulo_CuandoExisteNombre_DeberiaLanzarExcepcion() {
        when(moduloRepository.existsByNombre("Modulo A")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                moduloService.crearModulo(crearDto, "any"));

        assertEquals("Ya existe un módulo con ese nombre", ex.getMessage());
        verify(moduloRepository).existsByNombre("Modulo A");
        verify(moduloRepository, never()).save(any());
    }

    @Test
    void actualizarModulo_DeberiaActualizarCampos() {
        Modulo existing = new Modulo();
        existing.setId(5L);
        existing.setNombre("Old");
        existing.setDescripcion("Old desc");
        existing.setIcono("old.png");
        existing.setPrecioMensual(BigDecimal.valueOf(9.99));

        when(moduloRepository.findById(5L)).thenReturn(Optional.of(existing));

        CrearModuloDTO upd = new CrearModuloDTO();
        upd.setNombre("New");
        upd.setDescripcion("New desc");
        upd.setIcono("new.png");
        upd.setPrecioMensual(BigDecimal.valueOf(19.99));

        Modulo saved = new Modulo();
        saved.setId(5L);
        saved.setNombre("New");
        saved.setDescripcion("New desc");
        saved.setIcono("new.png");
        saved.setPrecioMensual(BigDecimal.valueOf(19.99));

        when(moduloRepository.save(any(Modulo.class))).thenReturn(saved);

        var dto = moduloService.actualizarModulo(5L, upd, "empleadoId");

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("New", dto.getNombre());

        verify(moduloRepository).findById(5L);
        verify(moduloRepository).save(any(Modulo.class));
    }

    @Test
    void toggleActivoModulo_DeberiaInvertirActivo() {
        Modulo existing = new Modulo();
        existing.setId(7L);
        existing.setActivo(true);

        when(moduloRepository.findById(7L)).thenReturn(Optional.of(existing));

        Modulo saved = new Modulo();
        saved.setId(7L);
        saved.setActivo(false);

        when(moduloRepository.save(any(Modulo.class))).thenReturn(saved);

        var dto = moduloService.toggleActivoModulo(7L, "empleado");

        assertNotNull(dto);
        assertEquals(7L, dto.getId());
        assertFalse(dto.getActivo());

        verify(moduloRepository).findById(7L);
        verify(moduloRepository).save(any(Modulo.class));
    }
}
