package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.Notificacion;
import com.imagicode.agorasoftadmin.entidades.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Listar notificaciones de una plaza ordenadas por fecha (más reciente primero)
    @Query("SELECT n FROM Notificacion n WHERE n.plaza.id = :plazaId ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByPlazaIdOrderByFechaCreacionDesc(@Param("plazaId") Long plazaId);

    // Notificaciones no leídas de una plaza
    @Query("SELECT n FROM Notificacion n WHERE n.plaza.id = :plazaId AND n.leida = false " +
            "ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesNoLeidasByPlazaId(@Param("plazaId") Long plazaId);

    // Contar notificaciones no leídas
    @Query("SELECT COUNT(n) FROM Notificacion n WHERE n.plaza.id = :plazaId AND n.leida = false")
    Long countNotificacionesNoLeidasByPlazaId(@Param("plazaId") Long plazaId);

    // Notificaciones por tipo
    @Query("SELECT n FROM Notificacion n WHERE n.plaza.id = :plazaId AND n.tipo = :tipo " +
            "ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByPlazaIdAndTipo(@Param("plazaId") Long plazaId,
            @Param("tipo") TipoNotificacion tipo);

    // Notificaciones enviadas por un empleado
    @Query("SELECT n FROM Notificacion n WHERE n.enviadoPor.id = :empleadoId " +
            "ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesEnviadasPorEmpleado(@Param("empleadoId") Long empleadoId);

    // Notificaciones urgentes no leídas (todas las plazas)
    @Query("SELECT n FROM Notificacion n WHERE n.tipo = 'URGENTE' AND n.leida = false " +
            "ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findNotificacionesUrgentesNoLeidas();

    // Notificaciones en un rango de fechas
    @Query("SELECT n FROM Notificacion n WHERE n.fechaCreacion BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findByFechaCreacionBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    // Estadísticas de notificaciones por tipo
    @Query("SELECT n.tipo, COUNT(n) FROM Notificacion n GROUP BY n.tipo")
    List<Object[]> countNotificacionesByTipo();

    // Eliminar notificaciones antiguas leídas (para limpieza)
    @Query("DELETE FROM Notificacion n WHERE n.leida = true AND n.fechaCreacion < :fechaLimite")
    void deleteNotificacionesAntiguasLeidas(@Param("fechaLimite") LocalDateTime fechaLimite);
}