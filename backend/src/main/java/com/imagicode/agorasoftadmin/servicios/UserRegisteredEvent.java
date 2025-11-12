package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.entidades.Usuario;

/**
 * Evento de dominio: usuario registrado.
 * Se publica con ApplicationEventPublisher.publishEvent(new UserRegisteredEvent(...))
 * 
 * Mantener simple y dentro de la capa de servicios para no crear carpetas nuevas.
 */
public class UserRegisteredEvent {

    private final Usuario usuario;
    private final String rawPassword; // puede ser null

    public UserRegisteredEvent(Usuario usuario, String rawPassword) {
        this.usuario = usuario;
        this.rawPassword = rawPassword;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * El password en texto plano (opcional).
     * Solo debe usarse para enviar correo de bienvenida con credenciales iniciales.
     * No debe loguearse ni persistirse.
     */
    public String getRawPassword() {
        return rawPassword;
    }
}