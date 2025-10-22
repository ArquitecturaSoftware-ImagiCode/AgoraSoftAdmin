package com.imagicode.agorasoftadmin.servicios;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender emailsender;

    public EmailService(final JavaMailSender emailsender) {
        this.emailsender = emailsender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("arquitest4@gmail.com");
        emailsender.send(message);
    }

    // Envío HTML
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
