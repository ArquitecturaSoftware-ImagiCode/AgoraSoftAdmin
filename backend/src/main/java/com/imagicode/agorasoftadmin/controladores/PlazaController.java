package com.imagicode.agorasoftadmin.controladores;

import com.imagicode.agorasoftadmin.dto.*;
import com.imagicode.agorasoftadmin.entidades.EstadoPlaza;
import com.imagicode.agorasoftadmin.servicios.PlazaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/plazas")
@CrossOrigin(origins = "*")
public class PlazaController {

    @Autowired
    private PlazaService plazaService;

    /**
     * Crear nueva plaza (desde frontend público)
     * POST /api/plazas (sin autenticación)
     */
    @PostMapping("/public")
    public ResponseEntity<?> crearPlaza(@Valid @RequestBody CrearPlazaDTO dto) {
        try {
            PlazaDTO plazaCreada = plazaService.crearPlaza(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(plazaCreada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener todas las plazas
     * GET /api/admin/plazas
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodasLasPlazas(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<PlazaDTO> plazas = plazaService.obtenerTodasLasPlazas();
            return ResponseEntity.ok(plazas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener plaza por ID
     * GET /api/admin/plazas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPlazaPorId(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            PlazaDTO plaza = plazaService.obtenerPlazaPorId(id);
            return ResponseEntity.ok(plaza);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener plazas pendientes de aprobación
     * GET /api/admin/plazas/pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<?> obtenerPlazasPendientes(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<PlazaDTO> plazas = plazaService.obtenerPlazasPendientes();
            return ResponseEntity.ok(plazas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener plazas por estado
     * GET /api/admin/plazas/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPlazasPorEstado(
            @PathVariable EstadoPlaza estado,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<PlazaDTO> plazas = plazaService.obtenerPlazasPorEstado(estado);
            return ResponseEntity.ok(plazas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Buscar plazas
     * GET /api/admin/plazas/buscar?q={searchTerm}
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPlazas(
            @RequestParam String q,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<PlazaDTO> plazas = plazaService.buscarPlazas(q);
            return ResponseEntity.ok(plazas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Aprobar plaza
     * POST /api/admin/plazas/{id}/aprobar
     */
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarPlaza(
            @PathVariable Long id,
            @Valid @RequestBody AprobarPlazaDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            PlazaDTO plazaAprobada = plazaService.aprobarPlaza(id, clerkUserId, dto);
            return ResponseEntity.ok(plazaAprobada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Rechazar plaza
     * POST /api/admin/plazas/{id}/rechazar
     */
    @PostMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarPlaza(
            @PathVariable Long id,
            @Valid @RequestBody RechazarPlazaDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            PlazaDTO plazaRechazada = plazaService.rechazarPlaza(id, clerkUserId, dto);
            return ResponseEntity.ok(plazaRechazada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Suspender plaza
     * POST /api/admin/plazas/{id}/suspender
     */
    @PostMapping("/{id}/suspender")
    public ResponseEntity<?> suspenderPlaza(
            @PathVariable Long id,
            @Valid @RequestBody SuspenderPlazaDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            PlazaDTO plazaSuspendida = plazaService.suspenderPlaza(id, clerkUserId, dto);
            return ResponseEntity.ok(plazaSuspendida);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Activar plaza suspendida
     * POST /api/admin/plazas/{id}/activar
     */
    @PostMapping("/{id}/activar")
    public ResponseEntity<?> activarPlaza(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            PlazaDTO plazaActivada = plazaService.activarPlaza(id, clerkUserId);
            return ResponseEntity.ok(plazaActivada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener plazas con pagos vencidos
     * GET /api/admin/plazas/pagos-vencidos
     */
    @GetMapping("/pagos-vencidos")
    public ResponseEntity<?> obtenerPlazasConPagosVencidos(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<PlazaDTO> plazas = plazaService.obtenerPlazasConPagosVencidos();
            return ResponseEntity.ok(plazas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Método auxiliar para crear respuestas de error
     */
    private Map<String, String> crearRespuestaError(String mensaje) {
        Map<String, String> error = new HashMap<>();
        error.put("error", mensaje);
        return error;
    }
}