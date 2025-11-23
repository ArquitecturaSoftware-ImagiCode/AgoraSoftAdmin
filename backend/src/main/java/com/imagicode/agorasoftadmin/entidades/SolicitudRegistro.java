package com.imagicode.agorasoftadmin.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitud_registro")
public class SolicitudRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // datos de la plaza solicitada
    @Column(name = "plaza_nombre")
    private String plazaNombre;

    @Column(name = "representante")
    private String representante;

    @Column(name = "correo")
    private String correo;

    @Column(name = "observaciones", length = 2000)
    private String observaciones;

    @Column(name = "estado", length = 50)
    private String estado; // PENDIENTE, APROBADA, RECHAZADA

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "actualizado_at")
    private LocalDateTime actualizadoAt;

    public SolicitudRegistro() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlazaNombre() { return plazaNombre; }
    public void setPlazaNombre(String plazaNombre) { this.plazaNombre = plazaNombre; }

    public String getRepresentante() { return representante; }
    public void setRepresentante(String representante) { this.representante = representante; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getActualizadoAt() { return actualizadoAt; }
    public void setActualizadoAt(LocalDateTime actualizadoAt) { this.actualizadoAt = actualizadoAt; }
}
