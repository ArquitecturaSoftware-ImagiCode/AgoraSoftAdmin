package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_pagos")
public class HistorialPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suscripcion_id", nullable = false)
    private Suscripcion suscripcion;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, length = 100)
    private String metodoPago;

    @Column(length = 200)
    private String comprobante;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estado;

    // Empleado que registró el pago manualmente (si aplica)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por_empleado_id")
    private Empleado registradoPor;

    // Constructores
    public HistorialPago() {
        this.fechaPago = LocalDateTime.now();
        this.estado = EstadoTransaccion.COMPLETADO;
    }

    public HistorialPago(Suscripcion suscripcion, BigDecimal monto, String metodoPago) {
        this();
        this.suscripcion = suscripcion;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public void setEstado(EstadoTransaccion estado) {
        this.estado = estado;
    }

    public Empleado getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Empleado registradoPor) {
        this.registradoPor = registradoPor;
    }
}