package com.imagicode.agorasoftadmin.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;

public interface SolicitudRepository extends JpaRepository<SolicitudRegistro, Long> {

    Page<SolicitudRegistro> findByEstadoIgnoreCase(String estado, Pageable pageable);

    // búsqueda simple por nombre de plaza o representante (containing, case-insensitive)
    Page<SolicitudRegistro> findByPlazaNombreContainingIgnoreCaseOrRepresentanteContainingIgnoreCase(
            String plazaNombre, String representante, Pageable pageable);

    long countByEstado(String estado);

}
