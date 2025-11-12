package com.imagicode.agorasoftadmin.servicios;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.EstadoTransaccion;
import com.imagicode.agorasoftadmin.entidades.HistorialPago;
import com.imagicode.agorasoftadmin.entidades.TipoPago;
import com.imagicode.agorasoftadmin.repositorios.HistorialPagoRepository;

@Service
public class HistorialPagoService {

    private final HistorialPagoRepository historialPagoRepository;

    public HistorialPagoService(HistorialPagoRepository historialPagoRepository) {
        this.historialPagoRepository = historialPagoRepository;
    }

    public List<HistorialPago> obtenerTodosLosPagos() {
        return historialPagoRepository.findAll();
    }

    public Optional<HistorialPago> obtenerPagoPorId(Long id) {
        return historialPagoRepository.findById(id);
    }

    public HistorialPago crearPago(HistorialPago historialPago) {
        return historialPagoRepository.save(historialPago);
    }

    public HistorialPago actualizarPago(HistorialPago historialPago) {
        return historialPagoRepository.save(historialPago);
    }

    public void eliminarPago(Long id) {
        historialPagoRepository.deleteById(id);
    }

    // Métodos de consulta
    public List<HistorialPago> obtenerPagosPorTipo(TipoPago tipoPago) {
        return historialPagoRepository.findByTipoPago(tipoPago);
    }

    public List<HistorialPago> obtenerPagosPorEstado(EstadoTransaccion estado) {
        return historialPagoRepository.findByEstado(estado);
    }

    public List<HistorialPago> obtenerPagosNomina() {
        return historialPagoRepository.findPagosNomina();
    }

    public List<HistorialPago> obtenerPagosServicios() {
        return historialPagoRepository.findPagosServicios();
    }

    public List<HistorialPago> obtenerPagosPorEmpleado(Long empleadoId) {
        return historialPagoRepository.findByEmpleadoId(empleadoId);
    }

    public List<HistorialPago> obtenerPagosPorOrganizacion(Integer organizacionId) {
        return historialPagoRepository.findByOrganizacionId(organizacionId);
    }

    public List<HistorialPago> obtenerPagosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return historialPagoRepository.findByFechaPagoBetween(fechaInicio, fechaFin);
    }

    public List<HistorialPago> obtenerPagosMesActual() {
        return historialPagoRepository.findPagosMesActual();
    }

    // Métodos de estadísticas
    public BigDecimal obtenerTotalPagosPorTipo(TipoPago tipoPago) {
        BigDecimal total = historialPagoRepository.sumMontoByTipoPago(tipoPago);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal obtenerTotalPagosPorEstado(EstadoTransaccion estado) {
        BigDecimal total = historialPagoRepository.sumMontoByEstado(estado);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal obtenerTotalPagosNomina() {
        return obtenerTotalPagosPorTipo(TipoPago.NOMINA);
    }

    public BigDecimal obtenerTotalPagosServicios() {
        return obtenerTotalPagosPorTipo(TipoPago.SERVICIO);
    }

    // Métodos de actualización de estado
    public HistorialPago marcarComoCompletado(Long id) {
        Optional<HistorialPago> pagoOpt = historialPagoRepository.findById(id);
        if (pagoOpt.isPresent()) {
            HistorialPago pago = pagoOpt.get();
            pago.setEstado(EstadoTransaccion.COMPLETADO);
            return historialPagoRepository.save(pago);
        }
        return null;
    }

    public HistorialPago marcarComoPendiente(Long id) {
        Optional<HistorialPago> pagoOpt = historialPagoRepository.findById(id);
        if (pagoOpt.isPresent()) {
            HistorialPago pago = pagoOpt.get();
            pago.setEstado(EstadoTransaccion.PENDIENTE);
            return historialPagoRepository.save(pago);
        }
        return null;
    }

    public HistorialPago marcarComoCancelado(Long id) {
        Optional<HistorialPago> pagoOpt = historialPagoRepository.findById(id);
        if (pagoOpt.isPresent()) {
            HistorialPago pago = pagoOpt.get();
            pago.setEstado(EstadoTransaccion.FALLIDO);
            return historialPagoRepository.save(pago);
        }
        return null;
    }
}
