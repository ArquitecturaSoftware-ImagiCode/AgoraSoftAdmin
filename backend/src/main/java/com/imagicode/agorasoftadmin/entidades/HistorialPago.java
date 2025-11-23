package com.imagicode.agorasoftadmin.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_pagos")
public class HistorialPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

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

    @Column(length = 500)
    private String descripcion;

    // IDs simples para referencias (sin relaciones JPA para evitar problemas)
    @Column(name = "empleado_id")
    private Long empleadoId;

    @Column(name = "organizacion_id")
    private Integer organizacionId;

    @Column(name = "registrado_por_empleado_id")
    private Long registradoPorEmpleadoId;

    // Constructores
    public HistorialPago() {
        this.fechaPago = LocalDateTime.now();
        this.estado = EstadoTransaccion.COMPLETADO;
    }

    public HistorialPago(TipoPago tipoPago, BigDecimal monto, String metodoPago, String descripcion) {
        this();
        this.tipoPago = tipoPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    // Nuevos getters y setters
    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Integer getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Integer organizacionId) {
        this.organizacionId = organizacionId;
    }

    public Long getRegistradoPorEmpleadoId() {
        return registradoPorEmpleadoId;
    }

    public void setRegistradoPorEmpleadoId(Long registradoPorEmpleadoId) {
        this.registradoPorEmpleadoId = registradoPorEmpleadoId;
    }
}