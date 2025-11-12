package com.imagicode.agorasoftadmin.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "suscripciones")
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "plaza_id", nullable = false, unique = true)
    private Plaza plaza;

    @Column(nullable = false, length = 100)
    private String planActual;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoMensual;

    @Column(nullable = false, length = 50)
    private String metodoPago;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate proximaRenovacion;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    @Column
    private LocalDateTime fechaActualizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por_empleado_id")
    private Empleado creadoPor;

    // Constructores
    public Suscripcion() {
        this.fechaInicio = LocalDate.now();
        this.proximaRenovacion = LocalDate.now().plusMonths(1);
        this.estadoPago = EstadoPago.PENDIENTE;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Suscripcion(Plaza plaza, String planActual, BigDecimal montoMensual, 
                      String metodoPago, Empleado creadoPor) {
        this();
        this.plaza = plaza;
        this.planActual = planActual;
        this.montoMensual = montoMensual;
        this.metodoPago = metodoPago;
        this.creadoPor = creadoPor;
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

    public String getPlanActual() {
        return planActual;
    }

    public void setPlanActual(String planActual) {
        this.planActual = planActual;
    }

    public BigDecimal getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(BigDecimal montoMensual) {
        this.montoMensual = montoMensual;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getProximaRenovacion() {
        return proximaRenovacion;
    }

    public void setProximaRenovacion(LocalDate proximaRenovacion) {
        this.proximaRenovacion = proximaRenovacion;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Empleado getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Empleado creadoPor) {
        this.creadoPor = creadoPor;
    }

    // Métodos auxiliares
    public void renovar() {
        this.proximaRenovacion = this.proximaRenovacion.plusMonths(1);
        this.fechaActualizacion = LocalDateTime.now();
    }

    public boolean estaVencida() {
        return LocalDate.now().isAfter(this.proximaRenovacion);
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
