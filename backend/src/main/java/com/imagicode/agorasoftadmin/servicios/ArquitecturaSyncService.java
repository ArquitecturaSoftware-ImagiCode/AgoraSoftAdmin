package com.imagicode.agorasoftadmin.servicios;

import java.time.Duration;
import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.imagicode.agorasoftadmin.entidades.PlazaInstance;
import com.imagicode.agorasoftadmin.repositorios.PlazaInstanceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArquitecturaSyncService {

    private final RestTemplate rest = new RestTemplate();
    private final PlazaInstanceRepository instanceRepo;

    public ArquitecturaSyncService(PlazaInstanceRepository instanceRepo) {
        this.instanceRepo = instanceRepo;
        // Opcional: configurar timeouts si se quiere
    }

    /**
     * Notifica a todas las instancias activas de la plaza para invalidar sesiones.
     * Devuelve true si al menos una instancia respondió 2xx.
     */
    public boolean notifyInvalidateSessions(Long plazaId) {
        List<PlazaInstance> instances = instanceRepo.findByPlazaIdAndActiveTrue(plazaId);
        boolean ok = false;
        for (PlazaInstance inst : instances) {
            try {
                String url = inst.getBaseUrl().replaceAll("/$", "") + "/internal/invalidate-sessions";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                // puedes añadir autenticación entre servicios si deseas
                HttpEntity<String> req = new HttpEntity<>("{\"plazaId\":" + plazaId + "}", headers);
                ResponseEntity<String> resp = rest.postForEntity(url, req, String.class);
                if (resp.getStatusCode().is2xxSuccessful())
                    ok = true;
            } catch (Exception e) {
                // log y continuar; no bloqueamos por una instancia caída
                // logger.warn("Error notifying instance {}: {}", inst.getBaseUrl(),
                // e.getMessage());
            }
        }
        return ok;
    }
}