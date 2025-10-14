package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoftadmin.entidades.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByPlazaId(Long plazaId);
}
