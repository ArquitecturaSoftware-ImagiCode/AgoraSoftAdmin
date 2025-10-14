package com.imagicode.agorasoftadmin.entidades;

public enum TipoNotificacion {
    INFORMATIVA("Informativa"),
    ADVERTENCIA("Advertencia"),
    URGENTE("Urgente"),
    SISTEMA("Sistema");

    private final String descripcion;

    TipoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}