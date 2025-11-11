package com.imagicode.agorasoftadmin.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
// import com.imagicode.agorasoftadmin.servicios.EmailMessage;
// import com.imagicode.agorasoftadmin.servicios.EmailService;

/**
 * Servicio para integración con Clerk
 * Este es un stub - debe implementarse la integración real con la API de Clerk
 */
@Service
public class ClerkService {

    private final EmailService emailService;
    @Value("${notify.email.from:${spring.mail.username}}")
    private String from;

    public ClerkService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Crear usuario empleado en Clerk
     */
    public String crearUsuarioEmpleado(String correo, String nombre, String apellido) {
        // TODO: Implementar integración con API de Clerk
        // Por ahora retorna un ID simulado
        return "clerk_empleado_" + System.currentTimeMillis();
    }

    /**
     * Crear usuario representante de plaza en Clerk
     */
    public String crearUsuarioRepresentantePlaza(String correo, String nombre) {
        // TODO: Implementar integración con API de Clerk
        return "clerk_plaza_" + System.currentTimeMillis();
    }

    /**
     * Desactivar usuario en Clerk
     */
    public void desactivarUsuario(String clerkUserId) {
        // TODO: Implementar integración con API de Clerk
        System.out.println("Usuario desactivado en Clerk: " + clerkUserId);
    }

    /**
     * Activar usuario en Clerk
     */
    public void activarUsuario(String clerkUserId) {
        // TODO: Implementar integración con API de Clerk
        System.out.println("Usuario activado en Clerk: " + clerkUserId);
    }

    /**
     * Enviar correo de bienvenida
     */
    public void enviarCorreoBienvenida(String correo, String nombre) {
        String subject = "Bienvenido a AgoraSoft";
        String body = "Hola " + nombre
                + ",\n\nTu registro como empleado fue creado exitosamente.\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }

    // Envío real: registro de plaza recibido (invocado desde
    // PlazaService.crearPlaza)
    public void enviarCorreoRegistroPlazaRecibido(String correo, String nombrePlaza, String representante) {
        String subject = "Registro de plaza recibido";
        String body = "Hola " + (representante == null ? "" : representante + ",\n\n") +
                "Hemos recibido el registro de la plaza '" + nombrePlaza
                + "'. Nuestro equipo revisará la información y te notificaremos por este medio.\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }

    // Aprobación de plaza
    public void enviarCorreoAprobacionPlaza(String correo, String nombrePlaza, String representante) {
        String subject = "Plaza aprobada";
        String body = "Hola " + representante + ",\n\nTu plaza '" + nombrePlaza
                + "' ha sido aprobada. Ya puedes acceder al sistema.\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }

    // Rechazo de plaza
    public void enviarCorreoRechazoPlaza(String correo, String nombrePlaza, String motivo) {
        String subject = "Plaza rechazada";
        String body = "Hola,\n\nTu solicitud para la plaza '" + nombrePlaza + "' fue rechazada.\nMotivo: "
                + (motivo == null ? "Sin especificar" : motivo) + "\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }
}