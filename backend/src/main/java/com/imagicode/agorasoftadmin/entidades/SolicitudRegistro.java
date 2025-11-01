package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_registro")
public class SolicitudRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plaza_nombre", nullable = false)
    private String plazaNombre;

    @Column(name = "representante", nullable = false)
    private String representante;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "organizacion")
    private String organizacion;

    @Column(name = "rol")
    private String rol;

    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";

    @Column(name = "observaciones", columnDefinition = "text")
    private String observaciones;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "actualizado_at")
    private LocalDateTime actualizadoAt = LocalDateTime.now();

    public SolicitudRegistro() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlazaNombre() {
        return plazaNombre;
    }

    public void setPlazaNombre(String plazaNombre) {
        this.plazaNombre = plazaNombre;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getActualizadoAt() {
        return actualizadoAt;
    }

    public void setActualizadoAt(LocalDateTime actualizadoAt) {
        this.actualizadoAt = actualizadoAt;
    }
}