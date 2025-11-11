package com.imagicode.agorasoftadmin.servicios;

import com.imagicode.agorasoftadmin.entidades.Usuario;

/**
 * Evento publicado cuando un usuario se registra en el sistema.
 * Se usa con ApplicationEventPublisher.publishEvent(new
 * UserRegisteredEvent(...))
 */
public class UserRegisteredEvent {

    private final Usuario usuario;
    private final String rawPassword;

    public UserRegisteredEvent(Usuario usuario, String rawPassword) {
        this.usuario = usuario;
        this.rawPassword = rawPassword;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * El password en texto plano (opcional). No debe usarse en producción salvo
     * para casos controlados.
     */
    public String getRawPassword() {
        return rawPassword;
    }
}