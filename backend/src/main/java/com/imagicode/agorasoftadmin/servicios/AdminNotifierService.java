package com.imagicode.agorasoftadmin.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Notifica a un módulo administrativo externo (si está configurado).
 * La URL se inyecta por propiedad/ENV (ADMIN_VALIDATION_URL). Si está vacía, no
 * hace nada.
 */
@Service
public class AdminNotifierService {

    private final RestTemplate rest = new RestTemplate();

    @Value("${admin.validation.url:}")
    private String adminUrl; // ej: http://host:port/api/validaciones/nuevo-registro

    @Async
    public void notifyNuevoRegistro(Map<String, Object> payload) {
        try {
            if (adminUrl == null || adminUrl.isBlank()) {
                // No configurado: salida silenciosa (no rompe flujo)
                return;
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> resp = rest.postForEntity(adminUrl, request, String.class);
            // opcional: log de resp.getStatusCode()
        } catch (Exception e) {
            // log no bloqueante
            System.err.println("Error notificar nuevo registro: " + e.getMessage());
        }
    }
}