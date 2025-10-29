package com.imagicode.agorasoftadmin.controladores;

import com.imagicode.agorasoftadmin.dto.OrganizationDTO;
import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.servicios.OrganizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/organizations")
@CrossOrigin(origins = "*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * Obtener todas las organizaciones
     * GET /api/admin/organizations/all
     */
    @GetMapping("/all")
    public ResponseEntity<?> obtenerTodasLasOrganizaciones(HttpServletRequest request) {
        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            List<Organization> organizaciones = organizationService.obtenerOrganizaciones();
            return ResponseEntity.ok(organizaciones);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener organización por ID
     * GET /api/admin/organizations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOrganizacionPorId(
        @PathVariable Long id,
        HttpServletRequest request) {

    try {
        String clerkUserId = (String) request.getAttribute("clerkUserId");
        // if (clerkUserId == null) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        //             .body(crearRespuestaError("No autorizado"));
        // }

        return organizationService.obtenerOrganizacionPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok) // 🔹 Esto fuerza el tipo genérico correcto
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(crearRespuestaError("Organización no encontrada")));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(crearRespuestaError(e.getMessage()));
    }
}

    /**
     * Obtener organización por ClerkOrgId
     * GET /api/admin/organizations/clerk/{clerkOrgId}
     */
    @GetMapping("/clerk/{clerkOrgId}")
    public ResponseEntity<?> obtenerPorClerkOrgId(
            @PathVariable String clerkOrgId,
            HttpServletRequest request) {

        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            return organizationService.obtenerPorClerkOrgId(clerkOrgId)
                    .<ResponseEntity<?>>map(ResponseEntity::ok) // 🔹 Esto fuerza el tipo genérico correcto
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError("Organización no encontrada")));
            
                    

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Crear nueva organización
     * POST /api/admin/organizations
     */
    @PostMapping
    public ResponseEntity<?> crearOrganizacion(
            @Valid @RequestBody OrganizationDTO dto,
            HttpServletRequest request) {

        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            Organization organizacion = new Organization();
            organizacion.setClerkOrgId(dto.getClerkOrgId());
            organizacion.setNombre(dto.getNombre());
            organizacion.setActivo(dto.getActivo());
            organizacion.setCreatedAt(dto.getCreatedAt());
            organizacion.setUpdatedAt(dto.getUpdatedAt());
            // Si tienes un Usuario propietario, deberías obtenerlo desde el servicio correspondiente
            // organizacion.setPropietario(usuarioService.obtenerUsuarioPorId(dto.getPropietarioId()));

            Organization creada = organizationService.crearOrganizacion(organizacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Actualizar organización existente
     * PUT /api/admin/organizations/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOrganizacion(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationDTO dto,
            HttpServletRequest request) {

        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            Optional<Organization> orgOpt = organizationService.obtenerOrganizacionPorId(id);
            if (orgOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(crearRespuestaError("Organización no encontrada"));
            }

            Organization organizacion = orgOpt.get();
            organizacion.setNombre(dto.getNombre());
            organizacion.setActivo(dto.getActivo());
            organizacion.setUpdatedAt(dto.getUpdatedAt());

            Organization actualizada = organizationService.actualizarOrganizacion(organizacion);
            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Cambiar estado activo/inactivo
     * POST /api/admin/organizations/{id}/estado
     */
    @PostMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestParam boolean activo,
            HttpServletRequest request) {

        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            Organization actualizada = organizationService.cambiarEstadoOrganizacion(id, activo);
            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(crearRespuestaError("Organización no encontrada"));
            }

            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener todas las organizaciones activas
     * GET /api/admin/organizations/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<?> obtenerOrganizacionesActivas(HttpServletRequest request) {
        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            List<Organization> activas = organizationService.obtenerOrganizacionesActivas();
            return ResponseEntity.ok(activas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Eliminar organización por ID
     * DELETE /api/admin/organizations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrganizacion(
            @PathVariable Long id,
            HttpServletRequest request) {

        try {
            // String clerkUserId = (String) request.getAttribute("clerkUserId");
            // if (clerkUserId == null) {
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //             .body(crearRespuestaError("No autorizado"));
            // }

            organizationService.eliminarOrganizacion(id);
            return ResponseEntity.ok(Map.of("mensaje", "Organización eliminada correctamente"));

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
