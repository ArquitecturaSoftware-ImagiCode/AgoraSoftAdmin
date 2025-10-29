package com.imagicode.agorasoftadmin.controladores;

import com.imagicode.agorasoftadmin.dto.OrganizationModuloDTO;
import com.imagicode.agorasoftadmin.servicios.OrganizationModuloService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/organizations/{organizationId}/modulos")
@CrossOrigin(origins = "*")
public class OrganizationModuloController {

    @Autowired
    private OrganizationModuloService organizationModuloService;

    /**
     * Obtener todos los módulos de una organización
     * GET /api/admin/organizations/{organizationId}/modulos
     */
    @GetMapping
    public ResponseEntity<?> obtenerModulosDeOrganization(
            @PathVariable Long organizationId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<OrganizationModuloDTO> modulos = organizationModuloService
                    .obtenerModulosDeOrganization(organizationId);
            return ResponseEntity.ok(modulos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener módulos activos de una organización
     * GET /api/admin/organizations/{organizationId}/modulos/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<?> obtenerModulosActivosDeOrganization(
            @PathVariable Long organizationId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<OrganizationModuloDTO> modulos = organizationModuloService
                    .obtenerModulosActivosDeOrganization(organizationId);
            return ResponseEntity.ok(modulos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Activar módulo para una organización
     * POST /api/admin/organizations/{organizationId}/modulos/{moduloId}/activar
     */
    @PostMapping("/{moduloId}/activar")
    public ResponseEntity<?> activarModuloParaOrganization(
            @PathVariable Long organizationId,
            @PathVariable Long moduloId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            // TODO: Obtener información del admin desde la base de datos
            // Por ahora usamos valores temporales
            Long adminId = 1L; // Temporal - obtener del usuario autenticado
            String adminNombre = "Administrador"; // Temporal - obtener del usuario autenticado

            OrganizationModuloDTO resultado = organizationModuloService
                    .asignarModuloAOrganization(organizationId, moduloId, adminId, adminNombre);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Desactivar módulo para una organización
     * POST /api/admin/organizations/{organizationId}/modulos/{moduloId}/desactivar
     */
    @PostMapping("/{moduloId}/desactivar")
    public ResponseEntity<?> desactivarModuloParaOrganization(
            @PathVariable Long organizationId,
            @PathVariable Long moduloId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            // TODO: Obtener información del admin desde la base de datos
            Long adminId = 1L; // Temporal
            String adminNombre = "Administrador"; // Temporal

            OrganizationModuloDTO resultado = organizationModuloService
                    .desactivarModuloDeOrganization(organizationId, moduloId, adminId, adminNombre);

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Toggle estado de módulo para una organización
     * POST /api/admin/organizations/{organizationId}/modulos/{moduloId}/toggle
     */
    @PostMapping("/{moduloId}/toggle")
    public ResponseEntity<?> toggleModuloParaOrganization(
            @PathVariable Long organizationId,
            @PathVariable Long moduloId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            // TODO: Obtener información del admin desde la base de datos
            Long adminId = 1L; // Temporal
            String adminNombre = "Administrador"; // Temporal

            // Primero verificamos el estado actual
            OrganizationModuloService organizationModuloService = this.organizationModuloService;
            List<OrganizationModuloDTO> modulos = organizationModuloService
                    .obtenerModulosDeOrganization(organizationId);

            boolean moduloActivo = modulos.stream()
                    .filter(m -> m.getModuloId().equals(moduloId))
                    .findFirst()
                    .map(OrganizationModuloDTO::getActivo)
                    .orElse(false);

            OrganizationModuloDTO resultado;
            if (moduloActivo) {
                resultado = organizationModuloService
                        .desactivarModuloDeOrganization(organizationId, moduloId, adminId, adminNombre);
            } else {
                resultado = organizationModuloService
                        .asignarModuloAOrganization(organizationId, moduloId, adminId, adminNombre);
            }

            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener estado específico de un módulo para una organización
     * GET /api/admin/organizations/{organizationId}/modulos/{moduloId}
     */
    @GetMapping("/{moduloId}")
    public ResponseEntity<?> obtenerEstadoModuloOrganization(
            @PathVariable Long organizationId,
            @PathVariable Long moduloId,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<OrganizationModuloDTO> modulos = organizationModuloService
                    .obtenerModulosDeOrganization(organizationId);

            OrganizationModuloDTO modulo = modulos.stream()
                    .filter(m -> m.getModuloId().equals(moduloId))
                    .findFirst()
                    .orElse(null);

            if (modulo == null) {
                // Si no existe, creamos un DTO con estado inactivo
                modulo = new OrganizationModuloDTO();
                modulo.setOrganizationId(organizationId);
                modulo.setModuloId(moduloId);
                modulo.setActivo(false);
            }

            return ResponseEntity.ok(modulo);

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