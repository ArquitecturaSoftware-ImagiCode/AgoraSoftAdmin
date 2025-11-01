package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.dto.CrearSolicitudDTO;
import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;
import com.imagicode.agorasoftadmin.repositorios.SolicitudRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional
    public SolicitudRegistro crearSolicitud(CrearSolicitudDTO dto) {
        SolicitudRegistro s = new SolicitudRegistro();
        s.setPlazaNombre(dto.getPlazaNombre());
        s.setRepresentante(dto.getRepresentante());
        s.setCorreo(dto.getCorreo());
        s.setTelefono(dto.getTelefono());
        s.setOrganizacion(dto.getOrganizacion());
        s.setRol(dto.getRol());
        s.setEstado("PENDIENTE");
        s.setCreatedAt(LocalDateTime.now());
        return solicitudRepository.save(s);
    }

    public Page<SolicitudRegistro> listarPorEstado(String estado, Pageable pageable) {
        return solicitudRepository.findByEstadoIgnoreCase(estado == null ? "PENDIENTE" : estado, pageable);
    }

    @Transactional
    public SolicitudRegistro actualizarEstado(Long id, String estado, String observaciones) {
        var opt = solicitudRepository.findById(id);
        if (opt.isEmpty())
            return null;
        var s = opt.get();
        s.setEstado(estado);
        s.setObservaciones(observaciones);
        s.setActualizadoAt(LocalDateTime.now());
        return solicitudRepository.save(s);
    }

    // NUEVO: crear solicitud en estado PENDIENTE
    public SolicitudRegistro crearSolicitudPendiente(SolicitudRegistro s) {
        if (s.getEstado() == null || s.getEstado().isBlank())
            s.setEstado("PENDIENTE");
        if (s.getCreatedAt() == null)
            s.setCreatedAt(LocalDateTime.now());
        s.setActualizadoAt(LocalDateTime.now());
        return solicitudRepository.save(s);
    }

    // Listar con filtros
    public Page<SolicitudRegistro> listarSolicitudes(String estado, String search, int page, int size, String sortBy,
            String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction == null ? "DESC" : direction),
                sortBy == null ? "createdAt" : sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (search != null && !search.isBlank()) {
            return solicitudRepository.findByPlazaNombreContainingIgnoreCaseOrRepresentanteContainingIgnoreCase(search,
                    search,
                    pageable);
        } else if (estado != null && !estado.isBlank()) {
            return solicitudRepository.findByEstadoIgnoreCase(estado, pageable);
        } else {
            return solicitudRepository.findAll(pageable);
        }
    }

    // Aprobación / Rechazo
    public SolicitudRegistro actualizarEstado(Long id, String nuevoEstado, String quien, String comentario) {
        SolicitudRegistro s = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada: " + id));
        s.setEstado(nuevoEstado);
        s.setActualizadoAt(LocalDateTime.now());
        if (comentario != null && !comentario.isBlank()) {
            String prev = s.getObservaciones() == null ? "" : s.getObservaciones() + "\n";
            s.setObservaciones(prev + "[" + quien + " - " + LocalDateTime.now() + "]: " + comentario);
        }
        return solicitudRepository.save(s);
    }

    public Map<String, Long> obtenerEstadisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", solicitudRepository.count());
        stats.put("pendientes", solicitudRepository.countByEstado("PENDIENTE"));
        stats.put("aprobadas", solicitudRepository.countByEstado("APROBADA"));
        stats.put("rechazadas", solicitudRepository.countByEstado("RECHAZADA"));
        return stats;
    }
}