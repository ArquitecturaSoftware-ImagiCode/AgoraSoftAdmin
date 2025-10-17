package com.imagicode.agorasoftadmin.dto;

import com.imagicode.agorasoftadmin.entidades.EstadoPlaza;
import java.time.LocalDateTime;

public class PlazaDTO {

    private Long id;
    private String nombre;
    private String rut;
    private String direccion;
    private String telefono;
    private String emailContacto;
    private String representanteLegal;
    private LocalDateTime fechaRegistro;
    private EstadoPlaza estado;
    private String estadoDescripcion;

    // Información del usuario representante
    private String clerkUserId;

    // Información de aprobación
    private Long aprobadoPorId;
    private String aprobadoPorNombre;
    private LocalDateTime fechaAprobacion;

    // Información de rechazo
    private Long rechazadoPorId;
    private String rechazadoPorNombre;
    private LocalDateTime fechaRechazo;
    private String motivoRechazo;

    // Información de suspensión
    private Long suspendidoPorId;
    private String suspendidoPorNombre;
    private LocalDateTime fechaSuspension;
    private String motivoSuspension;

    // Estadísticas
    private int cantidadModulosActivos;
    private String planSuscripcion;
    private String estadoPago;

    private LocalDateTime fechaActualizacion;

    // Constructores
    public PlazaDTO() {
    }

    public PlazaDTO(Long id, String nombre, String rut, String direccion,
            String telefono, String emailContacto, String representanteLegal,
            LocalDateTime fechaRegistro, EstadoPlaza estado) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.emailContacto = emailContacto;
        this.representanteLegal = representanteLegal;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.estadoDescripcion = estado != null ? estado.getDescripcion() : null;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoPlaza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlaza estado) {
        this.estado = estado;
        this.estadoDescripcion = estado != null ? estado.getDescripcion() : null;
    }

    public String getEstadoDescripcion() {
        return estadoDescripcion;
    }

    public void setEstadoDescripcion(String estadoDescripcion) {
        this.estadoDescripcion = estadoDescripcion;
    }

    public String getClerkUserId() {
        return clerkUserId;
    }

    public void setClerkUserId(String clerkUserId) {
        this.clerkUserId = clerkUserId;
    }

    public Long getAprobadoPorId() {
        return aprobadoPorId;
    }

    public void setAprobadoPorId(Long aprobadoPorId) {
        this.aprobadoPorId = aprobadoPorId;
    }

    public String getAprobadoPorNombre() {
        return aprobadoPorNombre;
    }

    public void setAprobadoPorNombre(String aprobadoPorNombre) {
        this.aprobadoPorNombre = aprobadoPorNombre;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Long getRechazadoPorId() {
        return rechazadoPorId;
    }

    public void setRechazadoPorId(Long rechazadoPorId) {
        this.rechazadoPorId = rechazadoPorId;
    }

    public String getRechazadoPorNombre() {
        return rechazadoPorNombre;
    }

    public void setRechazadoPorNombre(String rechazadoPorNombre) {
        this.rechazadoPorNombre = rechazadoPorNombre;
    }

    public LocalDateTime getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(LocalDateTime fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Long getSuspendidoPorId() {
        return suspendidoPorId;
    }

    public void setSuspendidoPorId(Long suspendidoPorId) {
        this.suspendidoPorId = suspendidoPorId;
    }

    public String getSuspendidoPorNombre() {
        return suspendidoPorNombre;
    }

    public void setSuspendidoPorNombre(String suspendidoPorNombre) {
        this.suspendidoPorNombre = suspendidoPorNombre;
    }

    public LocalDateTime getFechaSuspension() {
        return fechaSuspension;
    }

    public void setFechaSuspension(LocalDateTime fechaSuspension) {
        this.fechaSuspension = fechaSuspension;
    }

    public String getMotivoSuspension() {
        return motivoSuspension;
    }

    public void setMotivoSuspension(String motivoSuspension) {
        this.motivoSuspension = motivoSuspension;
    }

    public int getCantidadModulosActivos() {
        return cantidadModulosActivos;
    }

    public void setCantidadModulosActivos(int cantidadModulosActivos) {
        this.cantidadModulosActivos = cantidadModulosActivos;
    }

    public String getPlanSuscripcion() {
        return planSuscripcion;
    }

    public void setPlanSuscripcion(String planSuscripcion) {
        this.planSuscripcion = planSuscripcion;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}