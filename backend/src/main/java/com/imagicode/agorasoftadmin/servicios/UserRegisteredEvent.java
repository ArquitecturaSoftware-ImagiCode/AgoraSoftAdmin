package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.entidades.Usuario;

/**
 * Evento de dominio: usuario registrado.
 * Mantener simple y dentro de la capa de servicios para no crear carpetas
 * nuevas.
 */
public class UserRegisteredEvent {
    private final Usuario usuario;
    private final String rawPassword; // puede ser null (no se usa por defecto)

    public UserRegisteredEvent(Usuario usuario, String rawPassword) {
        this.usuario = usuario;
        this.rawPassword = rawPassword;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getRawPassword() {
        return rawPassword;
    }
}