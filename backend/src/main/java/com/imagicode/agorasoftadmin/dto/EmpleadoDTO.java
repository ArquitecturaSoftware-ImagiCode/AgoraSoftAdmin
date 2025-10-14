package com.imagicode.agorasoftadmin.dto;

import com.imagicode.agorasoftadmin.entidades.RolEmpleado;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmpleadoDTO {

    private Long id;
    private String clerkUserId;
    private String nombre;
    private String apellido;
    private String correo;
    private RolEmpleado rol;
    private String rolDescripcion;
    private String departamento;
    private LocalDate fechaContratacion;
    private Boolean activo;
    private String telefono;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructores
    public EmpleadoDTO() {
    }

    public EmpleadoDTO(Long id, String clerkUserId, String nombre, String apellido,
            String correo, RolEmpleado rol, String departamento,
            LocalDate fechaContratacion, Boolean activo) {
        this.id = id;
        this.clerkUserId = clerkUserId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.rolDescripcion = rol != null ? rol.getDescripcion() : null;
        this.departamento = departamento;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClerkUserId() {
        return clerkUserId;
    }

    public void setClerkUserId(String clerkUserId) {
        this.clerkUserId = clerkUserId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
        this.rolDescripcion = rol != null ? rol.getDescripcion() : null;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}