package com.imagicode.agorasoftadmin.controladores;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.imagicode.agorasoftadmin.repositorios.PlazaRepository;
import com.imagicode.agorasoftadmin.servicios.ArquitecturaSyncService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Endpoints de sincronización simples para HU-58.
 */
@RestController
@RequestMapping("/api/arquitectura")
public class ArquitecturaSyncController {

    private final PlazaRepository plazaRepo;
    private final ArquitecturaSyncService syncService;

    public ArquitecturaSyncController(PlazaRepository plazaRepo, ArquitecturaSyncService syncService) {
        this.plazaRepo = plazaRepo;
        this.syncService = syncService;
    }

    /**
     * Desactiva (suspende) una plaza: actualiza estado DB y notifica instancias.
     */
    @PostMapping("/plaza/{plazaId}/deactivate")
    @Transactional
    public ResponseEntity<?> deactivatePlaza(@PathVariable Long plazaId) {
        var plazaOpt = plazaRepo.findById(plazaId);
        if (plazaOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var plaza = plazaOpt.get();
        plaza.setEstado(com.imagicode.agorasoftadmin.entidades.EstadoPlaza.SUSPENDIDA);
        plazaRepo.save(plaza);

        boolean notified = syncService.notifyInvalidateSessions(plazaId);
        return ResponseEntity.ok(Map.of("plazaId", plazaId, "estado", "SUSPENDIDA", "notified", notified));
    }

    /**
     * Reactiva (habilita) una plaza: actualiza estado DB y notifica instancias para
     * levantar revocación.
     */
    @PostMapping("/plaza/{plazaId}/activate")
    @Transactional
    public ResponseEntity<?> activatePlaza(@PathVariable Long plazaId) {
        var plazaOpt = plazaRepo.findById(plazaId);
        if (plazaOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var plaza = plazaOpt.get();
        plaza.setEstado(com.imagicode.agorasoftadmin.entidades.EstadoPlaza.ACTIVA);
        plazaRepo.save(plaza);

        // opcional: notificar instancias para reactivar sesiones (endpoint
        // /internal/clear-invalidations)
        // aquí llamamos al mismo mecanismo con un flag distinto si lo prefieres
        return ResponseEntity.ok(Map.of("plazaId", plazaId, "estado", "ACTIVA"));
    }
}