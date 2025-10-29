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

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL)
    private Set<PlazaModulo> plazasConModulo = new HashSet<>();

    // Constructores
    public Modulo() {
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Modulo(String nombre, String descripcion, String icono,
            BigDecimal precioMensual, boolean activo) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.precioMensual = precioMensual;
        this.activo = activo;
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

    public Set<PlazaModulo> getPlazasConModulo() {
        return plazasConModulo;
    }

    public void setPlazasConModulo(Set<PlazaModulo> plazasConModulo) {
        this.plazasConModulo = plazasConModulo;
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

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrganizationModulo> organizations = new HashSet<>();

    // Getter y Setter
    public Set<OrganizationModulo> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationModulo> organizations) {
        this.organizations = organizations;
    }

    // Método helper para contar organizaciones que tienen este módulo activo
    public int getCantidadOrganizacionesActivas() {
        return (int) organizations.stream()
                .filter(OrganizationModulo::getActivo)
                .count();
    }
}