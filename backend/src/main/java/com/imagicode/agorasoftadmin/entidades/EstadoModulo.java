package com.imagicode.agorasoftadmin.entidades;

public enum EstadoModulo {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    EN_PRUEBA("En Prueba");

    private final String descripcion;

    EstadoModulo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}