package com.imagicode.agorasoftadmin.controladores;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.dto.CrearHistorialPagoDTO;
import com.imagicode.agorasoftadmin.entidades.EstadoTransaccion;
import com.imagicode.agorasoftadmin.entidades.HistorialPago;
import com.imagicode.agorasoftadmin.entidades.TipoPago;
import com.imagicode.agorasoftadmin.servicios.HistorialPagoService;

@RestController
@RequestMapping("/api/historial-pagos")
public class HistorialPagoController {

    private final HistorialPagoService historialPagoService;

    public HistorialPagoController(HistorialPagoService historialPagoService) {
        this.historialPagoService = historialPagoService;
    }

    // GET: traer todos los pagos
    @GetMapping
    public List<HistorialPago> listarPagos() {
        return historialPagoService.obtenerTodosLosPagos();
    }

    // GET: traer pago por id
    @GetMapping("/{id}")
    public Optional<HistorialPago> obtenerPagoPorId(@PathVariable Long id) {
        return historialPagoService.obtenerPagoPorId(id);
    }

    // POST: crear pago general
    @PostMapping
    public HistorialPago crearPago(@RequestBody CrearHistorialPagoDTO dto) {
        System.out.println("Creando pago desde DTO: " + dto);
        
        try {
            // Convertir DTO a entidad
            HistorialPago historialPago = new HistorialPago();
            
            // Convertir tipo de pago
            if (dto.getTipoPago() != null) {
                historialPago.setTipoPago(TipoPago.valueOf(dto.getTipoPago()));
            }
            
            // Convertir fecha de pago
            if (dto.getFechaPago() != null) {
                historialPago.setFechaPago(dto.getFechaPago().atStartOfDay());
            } else {
                historialPago.setFechaPago(LocalDateTime.now());
            }
            
            // Establecer otros campos
            historialPago.setMonto(dto.getMonto());
            historialPago.setMetodoPago(dto.getMetodoPago());
            historialPago.setComprobante(dto.getComprobante());
            historialPago.setDescripcion(dto.getDescripcion());
            historialPago.setEmpleadoId(dto.getEmpleadoId());
            historialPago.setOrganizacionId(dto.getOrganizacionId());
            historialPago.setRegistradoPorEmpleadoId(dto.getRegistradoPorEmpleadoId());
            
            // Convertir estado
            if (dto.getEstado() != null) {
                historialPago.setEstado(EstadoTransaccion.valueOf(dto.getEstado()));
            } else {
                historialPago.setEstado(EstadoTransaccion.COMPLETADO);
            }
            
            System.out.println("Entidad convertida: " + historialPago);
            return historialPagoService.crearPago(historialPago);
            
        } catch (Exception e) {
            System.err.println("Error al crear pago: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el pago: " + e.getMessage());
        }
    }

    // PUT: actualizar pago
    @PutMapping("/{id}")
    public HistorialPago actualizarPago(@PathVariable Long id, @RequestBody HistorialPago historialPago) {
        historialPago.setId(id);
        return historialPagoService.actualizarPago(historialPago);
    }

    // DELETE: eliminar pago
    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id) {
        historialPagoService.eliminarPago(id);
    }

    // GET: traer pagos por tipo
    @GetMapping("/tipo/{tipoPago}")
    public List<HistorialPago> obtenerPagosPorTipo(@PathVariable TipoPago tipoPago) {
        return historialPagoService.obtenerPagosPorTipo(tipoPago);
    }

    // GET: traer pagos por estado
    @GetMapping("/estado/{estado}")
    public List<HistorialPago> obtenerPagosPorEstado(@PathVariable EstadoTransaccion estado) {
        return historialPagoService.obtenerPagosPorEstado(estado);
    }

    // GET: traer pagos de nómina
    @GetMapping("/nomina")
    public List<HistorialPago> obtenerPagosNomina() {
        return historialPagoService.obtenerPagosNomina();
    }

    // GET: traer pagos de servicios
    @GetMapping("/servicios")
    public List<HistorialPago> obtenerPagosServicios() {
        return historialPagoService.obtenerPagosServicios();
    }

    // GET: traer pagos por empleado
    @GetMapping("/empleado/{empleadoId}")
    public List<HistorialPago> obtenerPagosPorEmpleado(@PathVariable Long empleadoId) {
        return historialPagoService.obtenerPagosPorEmpleado(empleadoId);
    }

    // GET: traer pagos por organización
    @GetMapping("/organizacion/{organizacionId}")
    public List<HistorialPago> obtenerPagosPorOrganizacion(@PathVariable Integer organizacionId) {
        return historialPagoService.obtenerPagosPorOrganizacion(organizacionId);
    }

    // GET: traer pagos por rango de fechas
    @GetMapping("/fechas")
    public List<HistorialPago> obtenerPagosPorRangoFechas(
            @RequestParam LocalDateTime fechaInicio, 
            @RequestParam LocalDateTime fechaFin) {
        return historialPagoService.obtenerPagosPorRangoFechas(fechaInicio, fechaFin);
    }

    // GET: traer pagos del mes actual
    @GetMapping("/mes-actual")
    public List<HistorialPago> obtenerPagosMesActual() {
        return historialPagoService.obtenerPagosMesActual();
    }

    // GET: obtener totales por tipo
    @GetMapping("/totales/tipo/{tipoPago}")
    public BigDecimal obtenerTotalPagosPorTipo(@PathVariable TipoPago tipoPago) {
        return historialPagoService.obtenerTotalPagosPorTipo(tipoPago);
    }

    // GET: obtener totales por estado
    @GetMapping("/totales/estado/{estado}")
    public BigDecimal obtenerTotalPagosPorEstado(@PathVariable EstadoTransaccion estado) {
        return historialPagoService.obtenerTotalPagosPorEstado(estado);
    }

    // GET: obtener totales de nómina
    @GetMapping("/totales/nomina")
    public BigDecimal obtenerTotalPagosNomina() {
        return historialPagoService.obtenerTotalPagosNomina();
    }

    // GET: obtener totales de servicios
    @GetMapping("/totales/servicios")
    public BigDecimal obtenerTotalPagosServicios() {
        return historialPagoService.obtenerTotalPagosServicios();
    }

    // PUT: marcar como completado
    @PutMapping("/{id}/completar")
    public HistorialPago marcarComoCompletado(@PathVariable Long id) {
        return historialPagoService.marcarComoCompletado(id);
    }

    // PUT: marcar como pendiente
    @PutMapping("/{id}/pendiente")
    public HistorialPago marcarComoPendiente(@PathVariable Long id) {
        return historialPagoService.marcarComoPendiente(id);
    }

    // PUT: marcar como cancelado
    @PutMapping("/{id}/cancelar")
    public HistorialPago marcarComoCancelado(@PathVariable Long id) {
        return historialPagoService.marcarComoCancelado(id);
    }
}
