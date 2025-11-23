package com.imagicode.agorasoftadmin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CrearHistorialPagoDTO {
    
    private String tipoPago;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaPago;
    
    private BigDecimal monto;
    
    private String metodoPago;
    
    private String comprobante;
    
    private String estado;
    
    private String descripcion;
    
    private Long empleadoId;
    
    private Integer organizacionId;
    
    private Long registradoPorEmpleadoId;
    
    // Constructores
    public CrearHistorialPagoDTO() {}
    
    // Getters y Setters
    public String getTipoPago() {
        return tipoPago;
    }
    
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
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
