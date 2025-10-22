package com.imagicode.agorasoftadmin.notificaciones.listeners;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import com.imagicode.agorasoftadmin.entidades.Usuario;
import com.imagicode.agorasoftadmin.notificaciones.EmailTemplateRenderer;
import com.imagicode.agorasoftadmin.notificaciones.events.UserRegisteredEvent;
import com.imagicode.agorasoftadmin.servicios.EmailService;

@Component
public class UserNotificationListener {

    private final EmailService emailService;
    private final EmailTemplateRenderer renderer;

    @Value("${notify.email.from:${spring.mail.username}}")
    private String from;

    @Value("${notify.include-raw-password:false}")
    private boolean includeRawPassword;

    public UserNotificationListener(EmailService emailService, EmailTemplateRenderer renderer) {
        this.emailService = emailService;
        this.renderer = renderer;
    }

    // AFTER_COMMIT garantiza que solo enviamos si la transacción se confirmó
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRegistered(UserRegisteredEvent event) {
        Usuario u = event.getUsuario();

        Map<String, Object> model = new HashMap<>();
        model.put("nombre", u.getNombre());
        model.put("apellido", u.getApellido());
        model.put("organizacion", u.getOrganizacion());
        model.put("rol", u.getRol());
        model.put("correo", u.getCorreo());

        // Controla si se muestra la password (desaconsejado, default false)
        String rawPassword = includeRawPassword ? event.getRawPassword() : null;
        model.put("showPassword", includeRawPassword && rawPassword != null);
        model.put("password", rawPassword != null ? rawPassword : "");

        String subject = "Bienvenido a AgoraSoft - Registro exitoso";
        String html = renderer.renderHtml("email/user-registered", model);
        // opcional: también podrías renderizar txt si quieres multipart

        emailService.sendHtml(u.getCorreo(), subject, html, from);
    }
}