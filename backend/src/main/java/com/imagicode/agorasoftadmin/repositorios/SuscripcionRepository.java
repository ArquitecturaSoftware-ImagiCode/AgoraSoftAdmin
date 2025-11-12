package com.imagicode.agorasoftadmin.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.EstadoPago;
import com.imagicode.agorasoftadmin.entidades.Suscripcion;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

    // Buscar por plaza
    Optional<Suscripcion> findByPlazaId(Long plazaId);

    // Buscar por estado de pago
    List<Suscripcion> findByEstadoPago(EstadoPago estadoPago);

    // Buscar suscripciones vencidas
    @Query("SELECT s FROM Suscripcion s WHERE s.proximaRenovacion < :fechaActual")
    List<Suscripcion> findSuscripcionesVencidas(@Param("fechaActual") LocalDate fechaActual);

    // Buscar suscripciones que vencen pronto (próximos 7 días)
    @Query("SELECT s FROM Suscripcion s WHERE s.proximaRenovacion BETWEEN :fechaActual AND :fechaLimite")
    List<Suscripcion> findSuscripcionesQueVencenPronto(@Param("fechaActual") LocalDate fechaActual, 
                                                      @Param("fechaLimite") LocalDate fechaLimite);

    // Calcular ingresos mensuales
    @Query("SELECT SUM(s.montoMensual) FROM Suscripcion s WHERE s.estadoPago = 'PAGADO' AND MONTH(s.fechaActualizacion) = MONTH(CURRENT_DATE) AND YEAR(s.fechaActualizacion) = YEAR(CURRENT_DATE)")
    Double calcularIngresosMensuales();

    // Contar suscripciones por estado
    long countByEstadoPago(EstadoPago estadoPago);

    // Verificar si existe por plaza
    boolean existsByPlazaId(Long plazaId);
}
