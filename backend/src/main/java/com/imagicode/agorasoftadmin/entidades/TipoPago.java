package com.imagicode.agorasoftadmin.entidades;

public enum TipoPago {
    NOMINA("Nómina"),
    SERVICIO("Servicio"),
    SUSCRIPCION("Suscripción"),
    OTRO("Otro");

    private final String descripcion;

    TipoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
