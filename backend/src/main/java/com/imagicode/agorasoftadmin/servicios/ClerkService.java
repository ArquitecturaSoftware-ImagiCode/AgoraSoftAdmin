package com.imagicode.agorasoftadmin.servicios;

import org.springframework.stereotype.Service;

/**
 * Servicio para integración con Clerk
 * Este es un stub - debe implementarse la integración real con la API de Clerk
 */
@Service
public class ClerkService {

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
        // TODO: Implementar envío de correo
        System.out.println("Correo de bienvenida enviado a: " + correo);
    }

    /**
     * Enviar correo de aprobación de plaza
     */
    public void enviarCorreoAprobacionPlaza(String correo, String nombrePlaza, String representante) {
        // TODO: Implementar envío de correo
        System.out.println("Correo de aprobación enviado a: " + correo);
    }

    /**
     * Enviar correo de rechazo de plaza
     */
    public void enviarCorreoRechazoPlaza(String correo, String nombrePlaza, String motivo) {
        // TODO: Implementar envío de correo
        System.out.println("Correo de rechazo enviado a: " + correo);
    }
}