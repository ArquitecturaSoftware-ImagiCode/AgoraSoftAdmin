package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Suscripcion;
import com.imagicode.agorasoftadmin.entidades.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

    // Buscar suscripción de una plaza
    @Query("SELECT s FROM Suscripcion s WHERE s.plaza.id = :plazaId")
    Optional<Suscripcion> findByPlazaId(@Param("plazaId") Long plazaId);

    // Listar por estado de pago
    List<Suscripcion> findByEstadoPago(EstadoPago estadoPago);

    // Suscripciones vencidas
    @Query("SELECT s FROM Suscripcion s WHERE s.proximaRenovacion < :fechaActual AND s.estadoPago != 'CANCELADO'")
    List<Suscripcion> findSuscripcionesVencidas(@Param("fechaActual") LocalDate fechaActual);

    // Suscripciones próximas a vencer (en los próximos N días)
    @Query("SELECT s FROM Suscripcion s WHERE s.proximaRenovacion BETWEEN :fechaActual AND :fechaLimite " +
            "AND s.estadoPago = 'AL_DIA'")
    List<Suscripcion> findSuscripcionesProximasAVencer(@Param("fechaActual") LocalDate fechaActual,
            @Param("fechaLimite") LocalDate fechaLimite);

    // Contar por estado de pago
    long countByEstadoPago(EstadoPago estadoPago);

    // Suscripciones creadas por un empleado
    @Query("SELECT s FROM Suscripcion s WHERE s.creadoPor.id = :empleadoId")
    List<Suscripcion> findSuscripcionesCreadasPorEmpleado(@Param("empleadoId") Long empleadoId);

    // Calcular ingresos totales del mes
    @Query("SELECT SUM(s.montoMensual) FROM Suscripcion s WHERE s.estadoPago = 'AL_DIA'")
    Double calcularIngresosMensuales();

    // Suscripciones por plan
    @Query("SELECT s.planActual, COUNT(s) FROM Suscripcion s GROUP BY s.planActual")
    List<Object[]> countSuscripcionesByPlan();
}