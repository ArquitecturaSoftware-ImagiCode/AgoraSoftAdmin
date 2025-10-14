package com.imagicode.agorasoftadmin.dto;

public class ModuloEstadisticaDTO {

    private Long moduloId;
    private String nombreModulo;
    private String icono;
    private long cantidadPlazas;

    // Constructores
    public ModuloEstadisticaDTO() {
    }

    public ModuloEstadisticaDTO(Long moduloId, String nombreModulo, String icono, long cantidadPlazas) {
        this.moduloId = moduloId;
        this.nombreModulo = nombreModulo;
        this.icono = icono;
        this.cantidadPlazas = cantidadPlazas;
    }

    // Getters y Setters
    public Long getModuloId() {
        return moduloId;
    }

    public void setModuloId(Long moduloId) {
        this.moduloId = moduloId;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public long getCantidadPlazas() {
        return cantidadPlazas;
    }

    public void setCantidadPlazas(long cantidadPlazas) {
        this.cantidadPlazas = cantidadPlazas;
    }
}