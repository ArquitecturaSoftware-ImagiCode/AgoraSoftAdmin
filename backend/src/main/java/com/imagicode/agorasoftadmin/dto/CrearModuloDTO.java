package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CrearModuloDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El icono es obligatorio")
    @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
    private String icono;

    @NotNull(message = "El precio mensual es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private BigDecimal precioMensual;

    @NotNull(message = "El estado es obligatorio")
    private boolean activo;

    // Constructores
    public CrearModuloDTO() {
    }

    public CrearModuloDTO(String nombre, String descripcion, String icono, BigDecimal precioMensual, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.precioMensual = precioMensual;
        this.activo = activo;

    }

    // Getters y Setters
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

    public boolean getActivo(){
        return activo;
    }
}