package com.imagicode.agorasoftadmin.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailHealthController {
    @GetMapping("/api/email/health")
    public String health() {
        return "ok";
    }
}