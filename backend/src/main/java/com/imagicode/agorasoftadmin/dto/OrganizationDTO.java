package com.imagicode.agorasoftadmin.dto;

import java.time.LocalDateTime;

public class OrganizationDTO {

    private Long id;
    private String clerkOrgId;
    private String nombre;
    private Long propietario; // Solo el ID del propietario (Usuario)
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrganizationDTO() {
    }

    public OrganizationDTO(Long id, String clerkOrgId, String nombre, Long propietario,
                            Boolean activo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.clerkOrgId = clerkOrgId;
        this.nombre = nombre;
        this.propietario = propietario;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClerkOrgId() {
        return clerkOrgId;
    }

    public void setClerkOrgId(String clerkOrgId) {
        this.clerkOrgId = clerkOrgId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPropietario() {
        return propietario;
    }

    public void setPropietario(Long propietario) {
        this.propietario = propietario;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}