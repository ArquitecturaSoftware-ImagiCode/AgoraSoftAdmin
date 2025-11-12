package com.imagicode.agorasoftadmin.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;
import com.imagicode.agorasoftadmin.servicios.SolicitudService;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {

    @Autowired
    private SolicitudService service;

    // Listar (soporta filtros: estado, search, page, size, sortBy, direction)
    @GetMapping
    public Page<SolicitudRegistro> listar(
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction
    ) {
        return service.listarSolicitudes(estado, search, page, size, sortBy, direction);
    }

    // Obtener una solicitud por id
    @GetMapping("/{id}")
    public SolicitudRegistro getOne(@PathVariable Long id) {
        return service.listarSolicitudes(null, null, 0, Integer.MAX_VALUE, null, null)
                      .stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    // Aprobar
    @PutMapping("/{id}/aprobar")
    public SolicitudRegistro aprobar(@PathVariable Long id,
                                     @RequestParam(value = "quien", required = false) String quien,
                                     @RequestParam(value = "comentario", required = false) String comentario) {
        return service.actualizarEstado(id, "APROBADA", quien, comentario);
    }

    // Rechazar
    @PutMapping("/{id}/rechazar")
    public SolicitudRegistro rechazar(@PathVariable Long id,
                                     @RequestParam(value = "quien", required = false) String quien,
                                     @RequestParam(value = "comentario", required = false) String comentario) {
        return service.actualizarEstado(id, "RECHAZADA", quien, comentario);
    }

    // Estadísticas para widgets
    @GetMapping("/estadisticas")
    public Map<String, Long> estadisticas() {
        return service.obtenerEstadisticas();
    }
}
