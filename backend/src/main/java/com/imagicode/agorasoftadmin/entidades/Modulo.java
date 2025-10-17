package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 50)
    private String icono;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMensual;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column
    private LocalDateTime fechaActualizacion;

    // Empleado que creó el módulo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por_empleado_id")
    private Empleado creadoPor;

    // Empleado que actualizó por última vez
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actualizado_por_empleado_id")
    private Empleado actualizadoPor;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    private Set<PlazaModulo> plazasConModulo = new HashSet<>();

    // Constructores
    public Modulo() {
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Modulo(String nombre, String descripcion, String icono,
            BigDecimal precioMensual, Empleado creadoPor) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.precioMensual = precioMensual;
        this.creadoPor = creadoPor;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Empleado getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Empleado creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Empleado getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(Empleado actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public Set<PlazaModulo> getPlazasConModulo() {
        return plazasConModulo;
    }

    public void setPlazasConModulo(Set<PlazaModulo> plazasConModulo) {
        this.plazasConModulo = plazasConModulo;
    }

    // Métodos de negocio
    public void actualizar(Empleado empleado) {
        this.actualizadoPor = empleado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public int getCantidadPlazasContratadas() {
        return (int) plazasConModulo.stream()
                .filter(pm -> pm.getEstado() == EstadoModulo.ACTIVO)
                .count();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}