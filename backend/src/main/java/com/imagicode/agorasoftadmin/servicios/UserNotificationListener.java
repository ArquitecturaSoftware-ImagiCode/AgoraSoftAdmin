package com.imagicode.agorasoftadmin.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import com.imagicode.agorasoftadmin.entidades.Usuario;

/**
 * Listener de eventos de usuario para notificar por correo el registro
 * (AFTER_COMMIT).
 * Queda controlado por la propiedad notify.user-registration.enabled (por
 * defecto false).
 */
@Component
public class UserNotificationListener {

    private final EmailService emailService;
    private final EmailTemplateRenderer renderer;

    @Value("${notify.email.from:${spring.mail.username}}")
    private String from;

    @Value("${notify.include-raw-password:false}")
    private boolean includeRawPassword;

    @Value("${notify.user-registration.enabled:false}")
    private boolean userRegistrationEnabled;

    public UserNotificationListener(EmailService emailService, EmailTemplateRenderer renderer) {
        this.emailService = emailService;
        this.renderer = renderer;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRegistered(UserRegisteredEvent event) {
        if (!userRegistrationEnabled)
            return; // desactivado por config

        Usuario u = event.getUsuario();

        Map<String, Object> model = new HashMap<>();
        model.put("nombre", u.getNombre());
        model.put("apellido", u.getApellido());
        model.put("organizacion", u.getOrganizacion());
        model.put("rol", u.getRol());
        model.put("correo", u.getCorreo());

        String rawPassword = includeRawPassword ? event.getRawPassword() : null;
        model.put("showPassword", includeRawPassword && rawPassword != null);
        model.put("password", rawPassword != null ? rawPassword : "");

        String subject = "Bienvenido a AgoraSoft - Registro exitoso";
        String html = renderer.renderHtml("email/user-registered", model);

        EmailMessage msg = EmailMessage.builder()
                .to(u.getCorreo())
                .from(from)
                .subject(subject)
                .html(html)
                .build();

        emailService.send(msg);
    }
}