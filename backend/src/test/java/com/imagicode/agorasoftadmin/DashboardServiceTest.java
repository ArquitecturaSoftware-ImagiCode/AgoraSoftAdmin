package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.dto.DashboardEstadisticasDTO;
import com.imagicode.agorasoftadmin.entidades.EstadoPlaza;
import com.imagicode.agorasoftadmin.repositorios.*;
import com.imagicode.agorasoftadmin.servicios.DashboardService;
import com.imagicode.agorasoftadmin.servicios.PlazaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock private PlazaRepository plazaRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    @Mock private ModuloRepository moduloRepository;
    @Mock private SuscripcionRepository suscripcionRepository;
    @Mock private PlazaService plazaService;

    @InjectMocks private DashboardService dashboardService;

    @Test
    void obtenerEstadisticas_DeberiaConstruirDTO_CaminoFeliz() {
        when(plazaRepository.count()).thenReturn(10L);
        when(plazaRepository.countByEstado(EstadoPlaza.ACTIVA)).thenReturn(4L);
        when(plazaRepository.countByEstado(EstadoPlaza.PENDIENTE)).thenReturn(1L);
        when(plazaRepository.countByEstado(EstadoPlaza.SUSPENDIDA)).thenReturn(2L);
        when(plazaRepository.countByEstado(EstadoPlaza.INACTIVA)).thenReturn(3L);

        when(empleadoRepository.count()).thenReturn(5L);
        when(empleadoRepository.countByActivoTrue()).thenReturn(4L);

        when(suscripcionRepository.calcularIngresosMensuales()).thenReturn(1000.0);

        when(moduloRepository.count()).thenReturn(6L);
        when(moduloRepository.countByActivoTrue()).thenReturn(5L);

        // retorno de topModulos
        when(moduloRepository.findModulosMasContratados())
            .thenReturn(List.<Object[]>of(new Object[]{1L, "M1", "i1", 10L}));

        // evitar llamadas a plazaService.obtenerPlazaPorId devolviendo lista vacía de plazas
        when(plazaRepository.findTopNPlazasRecientes()).thenReturn(List.of());

        DashboardEstadisticasDTO dto = dashboardService.obtenerEstadisticas();

        assertNotNull(dto);
        assertEquals(10L, dto.getTotalPlazas());
        assertEquals(BigDecimal.valueOf(1000.0), dto.getIngresosMensuales());
        assertEquals(6L, dto.getTotalModulos());
        assertEquals(4L, dto.getPlazasActivas());
        verify(plazaRepository).count();
        verify(suscripcionRepository).calcularIngresosMensuales();
    }
}
