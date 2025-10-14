package com.imagicode.agorasoftadmin.entidades;

public enum EstadoPago {
    AL_DIA("Al Día"),
    PENDIENTE("Pendiente"),
    VENCIDO("Vencido"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}