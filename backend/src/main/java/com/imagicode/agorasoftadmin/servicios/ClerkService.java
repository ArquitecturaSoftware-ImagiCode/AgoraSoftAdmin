package com.imagicode.agorasoftadmin.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio para integración con Clerk y envío de correos asociados a eventos de
 * negocio.
 */
@Service
public class ClerkService {

    private final EmailService emailService;

    @Value("${notify.email.from:${spring.mail.username}}")
    private String from;

    public ClerkService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String crearUsuarioEmpleado(String correo, String nombre, String apellido) {
        // Integración real con Clerk si aplica (aquí no cambiamos comportamiento
        // actual)
        return "clerk_empleado_" + System.currentTimeMillis();
    }

    public String crearUsuarioRepresentantePlaza(String correo, String nombre) {
        return "clerk_plaza_" + System.currentTimeMillis();
    }

    public void desactivarUsuario(String clerkUserId) {
        System.out.println("Usuario desactivado en Clerk: " + clerkUserId);
    }

    public void activarUsuario(String clerkUserId) {
        System.out.println("Usuario activado en Clerk: " + clerkUserId);
    }

    // Envío real: bienvenida a empleado (ya se invoca desde EmpleadoService)
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

    // Envío real: registro de plaza recibido (nuevo, se invoca desde
    // PlazaService.crearPlaza)
    public void enviarCorreoRegistroPlazaRecibido(String correo, String nombrePlaza, String representante) {
        String subject = "Registro de plaza recibido";
        String body = "Hola " + representante + ",\n\nHemos recibido el registro de la plaza '" + nombrePlaza + "'. " +
                "Nuestro equipo revisará la información y te notificaremos por este medio.\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }

    // Envío real: aprobación de plaza (ya se invoca desde
    // PlazaService.aprobarPlaza)
    public void enviarCorreoAprobacionPlaza(String correo, String nombrePlaza, String representante) {
        String subject = "Plaza aprobada";
        String body = "Hola " + representante + ",\n\nTu plaza '" + nombrePlaza + "' ha sido aprobada. " +
                "Ya puedes acceder al sistema.\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }

    // Envío real: rechazo de plaza (ya se invoca desde PlazaService.rechazarPlaza)
    public void enviarCorreoRechazoPlaza(String correo, String nombrePlaza, String motivo) {
        String subject = "Plaza rechazada";
        String body = "Hola,\n\nTu solicitud para la plaza '" + nombrePlaza + "' fue rechazada.\nMotivo: " + motivo +
                "\n\nSaludos,\nAgoraSoft";
        EmailMessage msg = EmailMessage.builder()
                .to(correo)
                .from(from)
                .subject(subject)
                .text(body)
                .build();
        emailService.send(msg);
    }
}