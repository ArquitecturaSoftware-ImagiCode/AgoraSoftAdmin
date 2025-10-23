package com.imagicode.agorasoftadmin.entidades;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "\"Organizations\"")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"") 

    private Long id;

    @Column(name = "\"ClerkOrgId\"", nullable = false, unique = true)
    private String clerkOrgId;

    @Column(name = "\"Name\"", nullable = false)
    private String nombre;

    // Dueño de la organización (un usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"OwnerId\"", nullable = false)
    @JsonIgnore 
    private Usuario propietario;

    @Column(name = "\"IsActive\"", nullable = false)
    private Boolean activo = false;

    @Column(name = "\"CreatedAt\"", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;

    // Constructor vacío requerido por JPA
    public Organization() {
    }

    // Constructor personalizado
    public Organization(String clerkOrgId, String nombre, Usuario propietario) {
        this.clerkOrgId = clerkOrgId;
        this.nombre = nombre;
        this.propietario = propietario;
        this.activo = false;
        this.createdAt = LocalDateTime.now();
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

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
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