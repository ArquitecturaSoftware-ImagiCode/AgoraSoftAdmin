package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.DashboardEstadisticasDTO;
import com.imagicode.agorasoftadmin.dto.ModuloEstadisticaDTO;
import com.imagicode.agorasoftadmin.dto.PlazaDTO;
import com.imagicode.agorasoftadmin.entidades.EstadoPlaza;
import com.imagicode.agorasoftadmin.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

        @Autowired
        private PlazaRepository plazaRepository;

        @Autowired
        private EmpleadoRepository empleadoRepository;

        @Autowired
        private ModuloRepository moduloRepository;

        @Autowired
        private SuscripcionRepository suscripcionRepository;

        @Autowired
        private PlazaService plazaService;

        /**
         * Obtener estadísticas completas del dashboard
         */
        public DashboardEstadisticasDTO obtenerEstadisticas() {
                DashboardEstadisticasDTO estadisticas = new DashboardEstadisticasDTO();

                // Estadísticas de plazas
                estadisticas.setTotalPlazas(plazaRepository.count());
                estadisticas.setPlazasActivas(plazaRepository.countByEstado(EstadoPlaza.ACTIVA));
                estadisticas.setPlazasPendientes(plazaRepository.countByEstado(EstadoPlaza.PENDIENTE));
                estadisticas.setPlazasSuspendidas(plazaRepository.countByEstado(EstadoPlaza.SUSPENDIDA));
                estadisticas.setPlazasInactivas(plazaRepository.countByEstado(EstadoPlaza.INACTIVA));

                // Estadísticas de empleados
                estadisticas.setTotalEmpleados(empleadoRepository.count());
                estadisticas.setEmpleadosActivos(empleadoRepository.countByActivoTrue());

                // Estadísticas financieras
                Double ingresos = suscripcionRepository.calcularIngresosMensuales();
                estadisticas.setIngresosMensuales(ingresos != null ? BigDecimal.valueOf(ingresos) : BigDecimal.ZERO);
                estadisticas.setIngresosMesActual(estadisticas.getIngresosMensuales());

                // Estadísticas de módulos
                estadisticas.setTotalModulos(moduloRepository.count());
                estadisticas.setModulosActivos(moduloRepository.countByActivoTrue());

                // Top módulos contratados
                List<Object[]> topModulos = moduloRepository.findModulosMasContratados();
                List<ModuloEstadisticaDTO> topModulosDTO = topModulos.stream()
                                .limit(5)
                                .map(obj -> new ModuloEstadisticaDTO(
                                                ((Number) obj[0]).longValue(), // ID
                                                (String) obj[1], // Nombre
                                                (String) obj[2], // Icono
                                                ((Number) obj[3]).longValue() // Cantidad
                                ))
                                .collect(Collectors.toList());
                estadisticas.setTopModulosContratados(topModulosDTO);

                // Últimas plazas registradas
                List<PlazaDTO> ultimasPlazas = plazaRepository.findTopNPlazasRecientes()
                                .stream()
                                .limit(5)
                                .map(plaza -> plazaService.obtenerPlazaPorId(plaza.getId()))
                                .collect(Collectors.toList());
                estadisticas.setUltimasPlazasRegistradas(ultimasPlazas);

                // Plazas con pagos vencidos
                estadisticas.setPlazasConPagosVencidos(plazaService.obtenerPlazasConPagosVencidos());

                // Distribución por estado
                Map<String, Long> distribucion = new HashMap<>();
                distribucion.put("ACTIVA", estadisticas.getPlazasActivas());
                distribucion.put("PENDIENTE", estadisticas.getPlazasPendientes());
                distribucion.put("SUSPENDIDA", estadisticas.getPlazasSuspendidas());
                distribucion.put("INACTIVA", estadisticas.getPlazasInactivas());
                estadisticas.setDistribucionPorEstado(distribucion);

                return estadisticas;
        }
}