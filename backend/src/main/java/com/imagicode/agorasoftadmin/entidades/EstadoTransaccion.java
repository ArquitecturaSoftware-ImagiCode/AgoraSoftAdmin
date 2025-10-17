package com.imagicode.agorasoftadmin.entidades;

public enum EstadoTransaccion {
    COMPLETADO("Completado"),
    PENDIENTE("Pendiente"),
    FALLIDO("Fallido"),
    REEMBOLSADO("Reembolsado");
    
    private final String descripcion;
    
    EstadoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}