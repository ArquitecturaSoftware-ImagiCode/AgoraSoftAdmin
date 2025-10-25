package com.imagicode.agorasoftadmin.servicios;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio de envío de correo.
 * Exponer un método tipado (EmailMessage) mejora legibilidad y contrato.
 * Se mantienen métodos existentes para compatibilidad.
 */
@Service
public class EmailService {

    private final JavaMailSender emailsender;

    public EmailService(final JavaMailSender emailsender) {
        this.emailsender = emailsender;
    }

    /** Nuevo método recomendado: envío usando DTO de intención. */
    public void send(EmailMessage message) {
        if (message.getHtmlBody() != null && !message.getHtmlBody().isBlank()) {
            sendHtml(message.getTo(), message.getSubject(), message.getHtmlBody(), message.getFrom());
        } else {
            sendEmail(
                    message.getTo(),
                    message.getSubject(),
                    message.getTextBody() != null ? message.getTextBody() : "");
        }
    }

    /** Compatibilidad: envío texto plano. */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        // Nota: el remitente por defecto se toma del mail sender; si se requiere
        // override, usar send(EmailMessage).
        emailsender.send(message);
    }

    /** Compatibilidad: envío HTML directo. Preferir send(EmailMessage). */
    public void sendHtml(String to, String subject, String html, String from) {
        try {
            MimeMessage mime = emailsender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true); // true = HTML
            if (from != null && !from.isBlank()) {
                helper.setFrom(from);
            }
            emailsender.send(mime);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando correo HTML", e);
        }
    }
}