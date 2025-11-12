package com.imagicode.agorasoftadmin.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio responsable de enviar correos.
 *
 * Expone un método tipado send(EmailMessage) para mejorar legibilidad y contrato,
 * pero mantiene métodos de compatibilidad (sendEmail y sendHtml) para código legado.
 *
 * Preferir siempre el uso de EmailMessage.builder() para nuevos casos.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Remitente por defecto. Si EmailMessage no define 'from', se usa este.
     * Usa notify.email.from si existe; si no, cae en spring.mail.username.
     */
    @Value("${notify.email.from:${spring.mail.username}}")
    private String defaultFrom;

    public EmailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envío principal: construye un MimeMessage y decide si usar HTML o texto plano.
     * - Si htmlBody tiene contenido, se envía como HTML.
     * - Si no, usa textBody (o cadena vacía si es null).
     * Fallback de 'from' al default configurado.
     */
    public void send(EmailMessage msg) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
            helper.setTo(msg.getTo());
            helper.setSubject(msg.getSubject());
            helper.setFrom(msg.getFrom() != null && !msg.getFrom().isBlank() ? msg.getFrom() : defaultFrom);

            if (msg.getHtmlBody() != null && !msg.getHtmlBody().isBlank()) {
                helper.setText(msg.getHtmlBody(), true);
            } else {
                helper.setText(msg.getTextBody() == null ? "" : msg.getTextBody(), false);
            }

            mailSender.send(mime);
        } catch (Exception e) {
            // En un escenario real podrías: loguear, reintentar, publicar evento, etc.
            throw new RuntimeException("Error enviando correo: " + e.getMessage(), e);
        }
    }

    /**
     * Compatibilidad: envío de texto plano sin construir manualmente EmailMessage.
     * No permite especificar 'from' distinto al por defecto.
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body != null ? body : "");
        if (defaultFrom != null && !defaultFrom.isBlank()) {
            message.setFrom(defaultFrom);
        }
        mailSender.send(message);
    }

    /**
     * Compatibilidad: envío HTML directo.
     * Preferir send(EmailMessage) para mayor claridad y extensibilidad.
     */
    public void sendHtml(String to, String subject, String html, String from) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html != null ? html : "", true);
            String effectiveFrom = (from != null && !from.isBlank()) ? from : defaultFrom;
            if (effectiveFrom != null && !effectiveFrom.isBlank()) {
                helper.setFrom(effectiveFrom);
            }
            mailSender.send(mime);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando correo HTML", e);
        }
    }
}