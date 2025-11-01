package com.imagicode.agorasoftadmin.repositorios;

import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<SolicitudRegistro, Long> {

    Page<SolicitudRegistro> findByPlazaNombreContainingIgnoreCaseOrRepresentanteContainingIgnoreCase(
            String plazaNombre, String representante, Pageable pageable);

    Page<SolicitudRegistro> findByEstadoIgnoreCase(String estado, Pageable pageable);

    long countByEstado(String estado);
}