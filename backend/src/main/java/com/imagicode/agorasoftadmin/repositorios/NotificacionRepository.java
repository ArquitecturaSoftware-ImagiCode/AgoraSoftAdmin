package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoftadmin.entidades.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByPlazaIdOrderByCreatedAtDesc(Long plazaId);
}
