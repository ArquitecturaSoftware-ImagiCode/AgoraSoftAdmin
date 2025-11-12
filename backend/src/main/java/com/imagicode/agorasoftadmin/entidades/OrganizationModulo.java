package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "organization_modulos")
public class OrganizationModulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    @Column(nullable = false)
    private Boolean activo = false;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Column
    private LocalDateTime fechaActualizacion;

    // Información de quién activó/desactivó el módulo
    @Column(name = "activado_por_admin_id")
    private Long activadoPorAdminId;

    @Column(name = "activado_por_admin_nombre")
    private String activadoPorAdminNombre;

    // Constructores
    public OrganizationModulo() {
        this.fechaAsignacion = LocalDateTime.now();
    }

    public OrganizationModulo(Organization organization, Modulo modulo, Boolean activo) {
        this();
        this.organization = organization;
        this.modulo = modulo;
        this.activo = activo;
    }

    public OrganizationModulo(Organization organization, Modulo modulo, Boolean activo, 
                             Long activadoPorAdminId, String activadoPorAdminNombre) {
        this();
        this.organization = organization;
        this.modulo = modulo;
        this.activo = activo;
        this.activadoPorAdminId = activadoPorAdminId;
        this.activadoPorAdminNombre = activadoPorAdminNombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
        this.fechaActualizacion = LocalDateTime.now();
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

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}