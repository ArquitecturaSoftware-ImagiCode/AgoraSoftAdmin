package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "plaza_modulos")
public class PlazaModulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id", nullable = false)
    private Plaza plaza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    @Column(nullable = false)
    private LocalDateTime fechaActivacion;

    @Column
    private LocalDateTime fechaDesactivacion;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoModulo estado;

    // Empleado que activó el módulo para la plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activado_por_empleado_id")
    private Empleado activadoPor;

    // Constructores
    public PlazaModulo() {
        this.fechaActivacion = LocalDateTime.now();
        this.estado = EstadoModulo.ACTIVO;
    }

    public PlazaModulo(Plaza plaza, Modulo modulo, Empleado activadoPor) {
        this();
        this.plaza = plaza;
        this.modulo = modulo;
        this.activadoPor = activadoPor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public LocalDateTime getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(LocalDateTime fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public LocalDateTime getFechaDesactivacion() {
        return fechaDesactivacion;
    }

    public void setFechaDesactivacion(LocalDateTime fechaDesactivacion) {
        this.fechaDesactivacion = fechaDesactivacion;
    }

    public EstadoModulo getEstado() {
        return estado;
    }

    public void setEstado(EstadoModulo estado) {
        this.estado = estado;
    }

    public Empleado getActivadoPor() {
        return activadoPor;
    }

    public void setActivadoPor(Empleado activadoPor) {
        this.activadoPor = activadoPor;
    }

    // Métodos de negocio
    public void desactivar() {
        this.estado = EstadoModulo.INACTIVO;
        this.fechaDesactivacion = LocalDateTime.now();
    }

    public void activar() {
        this.estado = EstadoModulo.ACTIVO;
        this.fechaDesactivacion = null;
    }
}