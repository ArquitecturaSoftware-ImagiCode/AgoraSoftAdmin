package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.HistorialPago;
import com.imagicode.agorasoftadmin.entidades.EstadoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialPagoRepository extends JpaRepository<HistorialPago, Long> {

    // Historial de pagos de una suscripción ordenado por fecha descendente
    @Query("SELECT hp FROM HistorialPago hp WHERE hp.suscripcion.id = :suscripcionId " +
            "ORDER BY hp.fechaPago DESC")
    List<HistorialPago> findBySuscripcionIdOrderByFechaPagoDesc(@Param("suscripcionId") Long suscripcionId);

    // Pagos por estado
    List<HistorialPago> findByEstado(EstadoTransaccion estado);

    // Pagos en un rango de fechas
    @Query("SELECT hp FROM HistorialPago hp WHERE hp.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY hp.fechaPago DESC")
    List<HistorialPago> findByFechaPagoBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Pagos registrados por un empleado
    @Query("SELECT hp FROM HistorialPago hp WHERE hp.registradoPor.id = :empleadoId " +
            "ORDER BY hp.fechaPago DESC")
    List<HistorialPago> findPagosRegistradosPorEmpleado(@Param("empleadoId") Long empleadoId);

    // Calcular total de ingresos en un periodo
    @Query("SELECT SUM(hp.monto) FROM HistorialPago hp " +
            "WHERE hp.estado = 'COMPLETADO' " +
            "AND hp.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    Double calcularIngresosPorPeriodo(@Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Contar pagos por estado
    long countByEstado(EstadoTransaccion estado);

    // Últimos N pagos completados
    @Query("SELECT hp FROM HistorialPago hp WHERE hp.estado = 'COMPLETADO' " +
            "ORDER BY hp.fechaPago DESC")
    List<HistorialPago> findUltimosPagosCompletados();
}