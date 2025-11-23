package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;
import com.imagicode.agorasoftadmin.repositorios.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository repo;

    // Listar con filtros: estado opcional y búsqueda por texto
    public Page<SolicitudRegistro> listarSolicitudes(String estado, String search, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction == null ? "DESC" : direction), sortBy == null ? "createdAt" : sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (search != null && !search.isBlank()) {
            return repo.findByPlazaNombreContainingIgnoreCaseOrRepresentanteContainingIgnoreCase(search, search, pageable);
        } else if (estado != null && !estado.isBlank()) {
            return repo.findByEstadoIgnoreCase(estado, pageable);
        } else {
            return repo.findAll(pageable);
        }
    }

    // Cambio de estado (aprobación / rechazo)
    public SolicitudRegistro actualizarEstado(Long id, String nuevoEstado, String quien, String comentario) {
        SolicitudRegistro s = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada: " + id));
        s.setEstado(nuevoEstado);
        s.setActualizadoAt(LocalDateTime.now());
        // opcional: podríamos guardar quien y comentario en otra tabla; por simplicidad agregamos observaciones
        if (comentario != null && !comentario.isBlank()) {
            String prev = s.getObservaciones() == null ? "" : s.getObservaciones() + "\n";
            s.setObservaciones(prev + "[" + quien + " - " + LocalDateTime.now() + "]: " + comentario);
        }
        return repo.save(s);
    }

    // Estadísticas para el dashboard
    public Map<String, Long> obtenerEstadisticas() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", repo.count());
        stats.put("pendientes", repo.countByEstado("PENDIENTE"));
        stats.put("aprobadas", repo.countByEstado("APROBADA"));
        stats.put("rechazadas", repo.countByEstado("RECHAZADA"));
        return stats;
    }
}
