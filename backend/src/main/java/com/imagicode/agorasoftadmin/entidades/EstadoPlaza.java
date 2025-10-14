package com.imagicode.agorasoftadmin.entidades;

public enum EstadoPlaza {
    PENDIENTE("Pendiente de Aprobación"),
    ACTIVA("Activa"),
    SUSPENDIDA("Suspendida"),
    INACTIVA("Inactiva"),
    RECHAZADA("Rechazada");

    private final String descripcion;

    EstadoPlaza(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}