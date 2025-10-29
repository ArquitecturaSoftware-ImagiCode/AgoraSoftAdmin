package com.imagicode.agorasoftadmin.dto;

import java.time.LocalDateTime;

public class OrganizationModuloDTO {

    private Long id;
    private Long organizationId;
    private String organizationNombre;
    private Long moduloId;
    private String moduloNombre;
    private String moduloDescripcion;
    private String moduloIcono;
    private Boolean activo;
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaActualizacion;
    private Long activadoPorAdminId;
    private String activadoPorAdminNombre;

    // Constructores
    public OrganizationModuloDTO() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationNombre() {
        return organizationNombre;
    }

    public void setOrganizationNombre(String organizationNombre) {
        this.organizationNombre = organizationNombre;
    }

    public Long getModuloId() {
        return moduloId;
    }

    public void setModuloId(Long moduloId) {
        this.moduloId = moduloId;
    }

    public String getModuloNombre() {
        return moduloNombre;
    }

    public void setModuloNombre(String moduloNombre) {
        this.moduloNombre = moduloNombre;
    }

    public String getModuloDescripcion() {
        return moduloDescripcion;
    }

    public void setModuloDescripcion(String moduloDescripcion) {
        this.moduloDescripcion = moduloDescripcion;
    }

    public String getModuloIcono() {
        return moduloIcono;
    }

    public void setModuloIcono(String moduloIcono) {
        this.moduloIcono = moduloIcono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getActivadoPorAdminId() {
        return activadoPorAdminId;
    }

    public void setActivadoPorAdminId(Long activadoPorAdminId) {
        this.activadoPorAdminId = activadoPorAdminId;
    }

    public String getActivadoPorAdminNombre() {
        return activadoPorAdminNombre;
    }

    public void setActivadoPorAdminNombre(String activadoPorAdminNombre) {
        this.activadoPorAdminNombre = activadoPorAdminNombre;
    }
}