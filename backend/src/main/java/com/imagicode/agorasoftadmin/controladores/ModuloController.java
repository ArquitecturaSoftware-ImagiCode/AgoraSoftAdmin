package com.imagicode.agorasoftadmin.controladores;

import com.imagicode.agorasoftadmin.dto.CrearModuloDTO;
import com.imagicode.agorasoftadmin.dto.ModuloDTO;
import com.imagicode.agorasoftadmin.servicios.ModuloService;
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
@RequestMapping("/api/admin/modulos")
@CrossOrigin(origins = "*")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    /**
     * Crear nuevo módulo
     * POST /api/admin/modulos
     */
    @PostMapping
    public ResponseEntity<?> crearModulo(
            @Valid @RequestBody CrearModuloDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            ModuloDTO moduloCreado = moduloService.crearModulo(dto, clerkUserId);
            return ResponseEntity.status(HttpStatus.CREATED).body(moduloCreado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener todos los módulos
     * GET /api/admin/modulos
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosModulos(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<ModuloDTO> modulos = moduloService.obtenerTodosLosModulos();
            return ResponseEntity.ok(modulos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener módulos activos
     * GET /api/admin/modulos/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<?> obtenerModulosActivos(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<ModuloDTO> modulos = moduloService.obtenerModulosActivos();
            return ResponseEntity.ok(modulos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener módulo por ID
     * GET /api/admin/modulos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerModuloPorId(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            ModuloDTO modulo = moduloService.obtenerModuloPorId(id);
            return ResponseEntity.ok(modulo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Actualizar módulo
     * PUT /api/admin/modulos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarModulo(
            @PathVariable Long id,
            @Valid @RequestBody CrearModuloDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            ModuloDTO moduloActualizado = moduloService.actualizarModulo(id, dto, clerkUserId);
            return ResponseEntity.ok(moduloActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Activar/Desactivar módulo
     * POST /api/admin/modulos/{id}/toggle-activo
     */
    @PostMapping("/{id}/toggle-activo")
    public ResponseEntity<?> toggleActivoModulo(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            ModuloDTO modulo = moduloService.toggleActivoModulo(id, clerkUserId);
            return ResponseEntity.ok(modulo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener estadísticas de módulos más contratados
     * GET /api/admin/modulos/estadisticas/mas-contratados
     */
    @GetMapping("/estadisticas/mas-contratados")
    public ResponseEntity<?> obtenerModulosMasContratados(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<Object[]> estadisticas = moduloService.obtenerModulosMasContratados();
            return ResponseEntity.ok(estadisticas);

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