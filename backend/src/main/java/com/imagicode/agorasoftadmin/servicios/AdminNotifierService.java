package com.imagicode.agorasoftadmin.servicios;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminNotifierService {

    private final RestTemplate rest = new RestTemplate();
    private final String adminUrl = "http://localhost:8082/api/validaciones/nuevo-registro"; // ajustar

    @Async
    public void notifyNuevoRegistro(Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> resp = rest.postForEntity(adminUrl, request, String.class);
            // loguear si quieres: resp.getStatusCode()
        } catch (Exception e) {
            // log y no lanzar para no romper la transacción
            System.err.println("Error notificar nuevo registro: " + e.getMessage());
        }
    }
}