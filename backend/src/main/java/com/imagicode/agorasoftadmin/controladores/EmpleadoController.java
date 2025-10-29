package com.imagicode.agorasoftadmin.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.dto.ActualizarEmpleadoDTO;
import com.imagicode.agorasoftadmin.dto.CrearEmpleadoDTO;
import com.imagicode.agorasoftadmin.dto.EmpleadoDTO;
import com.imagicode.agorasoftadmin.entidades.RolEmpleado;
import com.imagicode.agorasoftadmin.servicios.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Crear nuevo empleado
     * POST /api/admin/empleados
     */
    // EmpleadoController.java
@PostMapping
public ResponseEntity<?> crearEmpleado(@RequestBody CrearEmpleadoDTO crearEmpleadoDTO) {
    System.out.println("[EmpleadoController] Petición recibida en crearEmpleado()");
    System.out.println("[EmpleadoController] Datos recibidos: " + crearEmpleadoDTO);

    try {
        // Llamamos al servicio que devuelve un EmpleadoDTO
        EmpleadoDTO nuevoEmpleado = empleadoService.crearEmpleado(crearEmpleadoDTO);
        System.out.println("[EmpleadoController] Empleado creado correctamente: " + nuevoEmpleado.getCorreo());

        // Respondemos con el DTO
        return ResponseEntity.ok(nuevoEmpleado);

    } catch (Exception e) {
        System.err.println("[EmpleadoController] Error al crear empleado: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al crear empleado: " + e.getMessage());
    }
}





    /**
     * Obtener todos los empleados
     * GET /api/admin/empleados
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosEmpleados(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<EmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
            return ResponseEntity.ok(empleados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener empleado por ID
     * GET /api/admin/empleados/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(id);
            return ResponseEntity.ok(empleado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener empleado actual por Clerk User ID
     * GET /api/admin/empleados/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> obtenerEmpleadoActual(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorClerkUserId(clerkUserId);
            return ResponseEntity.ok(empleado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Actualizar empleado
     * PUT /api/admin/empleados/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEmpleadoDTO dto,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            if (!empleadoService.tienePermiso(clerkUserId, "MANAGE_EMPLOYEES")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(crearRespuestaError("No tiene permisos para actualizar empleados"));
            }

            EmpleadoDTO empleadoActualizado = empleadoService.actualizarEmpleado(id, dto);
            return ResponseEntity.ok(empleadoActualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Desactivar empleado
     * POST /api/admin/empleados/{id}/desactivar
     */
    @PostMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarEmpleado(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            if (!empleadoService.tienePermiso(clerkUserId, "MANAGE_EMPLOYEES")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(crearRespuestaError("No tiene permisos para desactivar empleados"));
            }

            EmpleadoDTO empleado = empleadoService.desactivarEmpleado(id);
            return ResponseEntity.ok(empleado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Activar empleado
     * POST /api/admin/empleados/{id}/activar
     */
    @PostMapping("/{id}/activar")
    public ResponseEntity<?> activarEmpleado(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            if (!empleadoService.tienePermiso(clerkUserId, "MANAGE_EMPLOYEES")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(crearRespuestaError("No tiene permisos para activar empleados"));
            }

            EmpleadoDTO empleado = empleadoService.activarEmpleado(id);
            return ResponseEntity.ok(empleado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Buscar empleados
     * GET /api/admin/empleados/buscar?q={searchTerm}
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarEmpleados(
            @RequestParam String q,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<EmpleadoDTO> empleados = empleadoService.buscarEmpleados(q);
            return ResponseEntity.ok(empleados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener empleados activos
     * GET /api/admin/empleados/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<?> obtenerEmpleadosActivos(HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<EmpleadoDTO> empleados = empleadoService.obtenerEmpleadosActivos();
            return ResponseEntity.ok(empleados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener empleados por rol
     * GET /api/admin/empleados/rol/{rol}
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<?> obtenerEmpleadosPorRol(
            @PathVariable RolEmpleado rol,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<EmpleadoDTO> empleados = empleadoService.obtenerEmpleadosPorRol(rol);
            return ResponseEntity.ok(empleados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearRespuestaError(e.getMessage()));
        }
    }

    /**
     * Obtener empleados por departamento
     * GET /api/admin/empleados/departamento/{departamento}
     */
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<?> obtenerEmpleadosPorDepartamento(
            @PathVariable String departamento,
            HttpServletRequest request) {
        try {
            String clerkUserId = (String) request.getAttribute("clerkUserId");
            if (clerkUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(crearRespuestaError("No autorizado"));
            }

            List<EmpleadoDTO> empleados = empleadoService.obtenerEmpleadosPorDepartamento(departamento);
            return ResponseEntity.ok(empleados);

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