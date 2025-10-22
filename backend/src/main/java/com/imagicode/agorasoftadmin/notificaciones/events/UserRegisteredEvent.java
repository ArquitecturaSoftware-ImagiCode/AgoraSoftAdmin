package com.imagicode.agorasoftadmin.notificaciones.events;

import com.imagicode.agorasoftadmin.entidades.Usuario;

public class UserRegisteredEvent {
    private final Usuario usuario;
    // Por seguridad: solo dev. Evita guardar password en entidades.
    private final String rawPassword; // puede ser null

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