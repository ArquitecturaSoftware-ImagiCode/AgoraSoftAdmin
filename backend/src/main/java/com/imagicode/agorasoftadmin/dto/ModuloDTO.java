package com.imagicode.agorasoftadmin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ModuloDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String icono;
    private BigDecimal precioMensual;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Información del creador
    private Long creadoPorId;
    private String creadoPorNombre;

    // Estadísticas
    private int cantidadPlazasContratadas;

    // Constructores
    public ModuloDTO() {
    }

    public ModuloDTO(Long id, String nombre, String descripcion, String icono,
            BigDecimal precioMensual, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.precioMensual = precioMensual;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Long creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public String getCreadoPorNombre() {
        return creadoPorNombre;
    }

    public void setCreadoPorNombre(String creadoPorNombre) {
        this.creadoPorNombre = creadoPorNombre;
    }

    public int getCantidadPlazasContratadas() {
        return cantidadPlazasContratadas;
    }

    public void setCantidadPlazasContratadas(int cantidadPlazasContratadas) {
        this.cantidadPlazasContratadas = cantidadPlazasContratadas;
    }
}