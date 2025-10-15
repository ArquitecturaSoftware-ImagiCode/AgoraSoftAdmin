package com.imagicode.agorasoftadmin.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de Health Check
 * Proporciona endpoints públicos para verificar el estado del sistema
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    /**
     * Health check básico
     * GET /api/health
     * Endpoint público (no requiere autenticación)
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "AgoraSoftAdmin Backend");
        health.put("version", "1.0.0");
        health.put("environment", "development");

        return ResponseEntity.ok(health);
    }

    /**
     * Health check detallado con estado de base de datos
     * GET /api/health/detailed
     * Endpoint público (no requiere autenticación)
     */
    @GetMapping("/health/detailed")
    public ResponseEntity<?> detailedHealthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "AgoraSoftAdmin Backend");
        health.put("version", "1.0.0");

        // Verificar conexión a base de datos
        Map<String, Object> database = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            boolean isValid = connection.isValid(5);
            database.put("status", isValid ? "UP" : "DOWN");
            database.put("database", connection.getCatalog());
            database.put("url", connection.getMetaData().getURL());
        } catch (Exception e) {
            database.put("status", "DOWN");
            database.put("error", e.getMessage());
        }
        health.put("database", database);

        // Información del sistema
        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("osName", System.getProperty("os.name"));
        system.put("osVersion", System.getProperty("os.version"));
        system.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        system.put("freeMemory", Runtime.getRuntime().freeMemory());
        system.put("totalMemory", Runtime.getRuntime().totalMemory());
        health.put("system", system);

        return ResponseEntity.ok(health);
    }

    /**
     * Endpoint de ping simple
     * GET /api/ping
     * Endpoint público (no requiere autenticación)
     */
    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}