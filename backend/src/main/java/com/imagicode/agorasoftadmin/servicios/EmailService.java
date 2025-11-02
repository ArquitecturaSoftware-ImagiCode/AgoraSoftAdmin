package com.imagicode.agorasoftadmin.servicios;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Servicio responsable de enviar correos. Expone método tipado
 * send(EmailMessage).
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${notify.email.from:${spring.mail.username}}")
    private String defaultFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envía el EmailMessage. Elige html si está presente, sino text.
     */
    public void send(EmailMessage msg) {
        try {
            MimeMessage mm = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mm, "UTF-8");
            helper.setTo(msg.getTo());
            helper.setSubject(msg.getSubject());
            helper.setFrom(msg.getFrom() != null ? msg.getFrom() : defaultFrom);

            if (msg.getHtmlBody() != null && !msg.getHtmlBody().isBlank()) {
                helper.setText(msg.getHtmlBody(), true);
            } else {
                helper.setText(msg.getTextBody() == null ? "" : msg.getTextBody(), false);
            }

            mailSender.send(mm);
        } catch (Exception e) {
            // Loguear y reintentar/strategia según políticas (aquí se lanza runtime para
            // ver en dev)
            throw new RuntimeException("Error enviando correo: " + e.getMessage(), e);
        }
    }

    /**
     * Mantiene compatibilidad con llamadas antiguas.
     */
    public void sendEmail(String to, String subject, String text) {
        send(EmailMessage.builder().to(to).subject(subject).text(text).build());
    }
}