package com.imagicode.agorasoftadmin.servicios;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

}
