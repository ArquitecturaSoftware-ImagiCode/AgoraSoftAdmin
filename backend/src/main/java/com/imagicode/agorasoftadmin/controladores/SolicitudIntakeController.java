package com.imagicode.agorasoftadmin.controladores;

import com.imagicode.agorasoftadmin.dto.CrearSolicitudDTO;
import com.imagicode.agorasoftadmin.entidades.SolicitudRegistro;
import com.imagicode.agorasoftadmin.servicios.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudIntakeController {

    private final SolicitudService solicitudService;

    @Value("${admin.intake.token:}")
    private String intakeToken;

    public SolicitudIntakeController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<?> intakeSolicitud(
            @RequestHeader(value = "X-Admin-Intake-Token", required = false) String token,
            @Valid @RequestBody CrearSolicitudDTO dto) {

        if (intakeToken != null && !intakeToken.isBlank()) {
            if (token == null || !token.equals(intakeToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid intake token");
            }
        }

        SolicitudRegistro created = solicitudService.crearSolicitud(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Intake de solicitudes desde AgoraSoft (plazas).
     * Autenticado por token compartido en header X-INTAKE-TOKEN.
     */
    @PostMapping("/intake")
    public ResponseEntity<?> intake(@RequestHeader(value = "X-INTAKE-TOKEN", required = false) String token,
            @RequestBody Map<String, Object> payload) {
        if (!StringUtils.hasText(intakeToken) || !intakeToken.equals(token)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String correo = (String) payload.get("correo");
        String nombrePlaza = (String) payload.get("nombrePlaza");
        String representante = (String) payload.get("representante");
        String rolSolicitado = (String) payload.get("rolSolicitado");
        String observaciones = (String) payload.getOrDefault("observaciones", "");

        if (!StringUtils.hasText(correo) || !StringUtils.hasText(nombrePlaza) || !StringUtils.hasText(rolSolicitado)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "correo, nombrePlaza y rolSolicitado son obligatorios"));
        }

        SolicitudRegistro s = new SolicitudRegistro();
        s.setCorreo(correo);
        s.setPlazaNombre(nombrePlaza);
        s.setRepresentante(representante);
        s.setEstado("PENDIENTE");
        s.setCreatedAt(LocalDateTime.now());
        s.setActualizadoAt(LocalDateTime.now());
        s.setObservaciones(observaciones);

        SolicitudRegistro guardada = solicitudService.crearSolicitudPendiente(s);
        return ResponseEntity.status(201).body(Map.of("id", guardada.getId(), "estado", guardada.getEstado()));
    }
}