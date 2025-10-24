package com.imagicode.agorasoftadmin.repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.EstadoTransaccion;
import com.imagicode.agorasoftadmin.entidades.HistorialPago;
import com.imagicode.agorasoftadmin.entidades.TipoPago;

@Repository
public interface HistorialPagoRepository extends JpaRepository<HistorialPago, Long> {

    // Buscar por tipo de pago
    List<HistorialPago> findByTipoPago(TipoPago tipoPago);

    // Buscar por estado
    List<HistorialPago> findByEstado(EstadoTransaccion estado);

    // Buscar por método de pago
    List<HistorialPago> findByMetodoPago(String metodoPago);

    // Buscar por empleado
    List<HistorialPago> findByEmpleadoId(Long empleadoId);

    // Buscar por organización
    List<HistorialPago> findByOrganizacionId(Integer organizacionId);

    // Buscar por rango de fechas
    @Query("SELECT h FROM HistorialPago h WHERE h.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<HistorialPago> findByFechaPagoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                              @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar pagos de nómina
    @Query("SELECT h FROM HistorialPago h WHERE h.tipoPago = 'NOMINA'")
    List<HistorialPago> findPagosNomina();

    // Buscar pagos de servicios
    @Query("SELECT h FROM HistorialPago h WHERE h.tipoPago = 'SERVICIO'")
    List<HistorialPago> findPagosServicios();

    // Buscar por empleado que registró el pago
    List<HistorialPago> findByRegistradoPorEmpleadoId(Long empleadoId);

    // Buscar pagos del mes actual
    @Query("SELECT h FROM HistorialPago h WHERE MONTH(h.fechaPago) = MONTH(CURRENT_DATE) AND YEAR(h.fechaPago) = YEAR(CURRENT_DATE)")
    List<HistorialPago> findPagosMesActual();

    // Sumar total de pagos por tipo
    @Query("SELECT SUM(h.monto) FROM HistorialPago h WHERE h.tipoPago = :tipoPago")
    java.math.BigDecimal sumMontoByTipoPago(@Param("tipoPago") TipoPago tipoPago);

    // Sumar total de pagos por estado
    @Query("SELECT SUM(h.monto) FROM HistorialPago h WHERE h.estado = :estado")
    java.math.BigDecimal sumMontoByEstado(@Param("estado") EstadoTransaccion estado);
}