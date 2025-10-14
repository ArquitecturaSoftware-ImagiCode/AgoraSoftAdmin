package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plazas")
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    private String rut;

    @Column(nullable = false, length = 500)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String emailContacto;

    @Column(nullable = false, length = 200)
    private String representanteLegal;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoPlaza estado;

    // Relación con Usuario (Clerk) - Representante de la Plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Empleado que aprobó la plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprobado_por_empleado_id")
    private Empleado aprobadoPor;

    @Column
    private LocalDateTime fechaAprobacion;

    // Empleado que rechazó la plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rechazado_por_empleado_id")
    private Empleado rechazadoPor;

    @Column
    private LocalDateTime fechaRechazo;

    @Column(length = 500)
    private String motivoRechazo;

    // Empleado que suspendió la plaza
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspendido_por_empleado_id")
    private Empleado suspendidoPor;

    @Column
    private LocalDateTime fechaSuspension;

    @Column(length = 500)
    private String motivoSuspension;

    // Relación con Módulos contratados
    @OneToMany(mappedBy = "plaza", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlazaModulo> modulosContratados = new HashSet<>();

    // Relación con Suscripción
    @OneToOne(mappedBy = "plaza", cascade = CascadeType.ALL)
    private Suscripcion suscripcion;

    // Relación con Notificaciones
    @OneToMany(mappedBy = "plaza", cascade = CascadeType.ALL)
    private Set<Notificacion> notificaciones = new HashSet<>();

    @Column
    private LocalDateTime fechaActualizacion;

    // Constructores
    public Plaza() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoPlaza.PENDIENTE;
    }

    public Plaza(String nombre, String rut, String direccion, String telefono,
            String emailContacto, String representanteLegal) {
        this();
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.emailContacto = emailContacto;
        this.representanteLegal = representanteLegal;
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
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empleado getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(Empleado aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Empleado getRechazadoPor() {
        return rechazadoPor;
    }

    public void setRechazadoPor(Empleado rechazadoPor) {
        this.rechazadoPor = rechazadoPor;
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

    public Empleado getSuspendidoPor() {
        return suspendidoPor;
    }

    public void setSuspendidoPor(Empleado suspendidoPor) {
        this.suspendidoPor = suspendidoPor;
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

    public Set<PlazaModulo> getModulosContratados() {
        return modulosContratados;
    }

    public void setModulosContratados(Set<PlazaModulo> modulosContratados) {
        this.modulosContratados = modulosContratados;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public Set<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Set<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Métodos de negocio
    public void aprobar(Empleado empleado) {
        this.estado = EstadoPlaza.ACTIVA;
        this.aprobadoPor = empleado;
        this.fechaAprobacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void rechazar(Empleado empleado, String motivo) {
        this.estado = EstadoPlaza.RECHAZADA;
        this.rechazadoPor = empleado;
        this.fechaRechazo = LocalDateTime.now();
        this.motivoRechazo = motivo;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void suspender(Empleado empleado, String motivo) {
        this.estado = EstadoPlaza.SUSPENDIDA;
        this.suspendidoPor = empleado;
        this.fechaSuspension = LocalDateTime.now();
        this.motivoSuspension = motivo;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void activar() {
        this.estado = EstadoPlaza.ACTIVA;
        this.suspendidoPor = null;
        this.fechaSuspension = null;
        this.motivoSuspension = null;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void agregarModulo(PlazaModulo plazaModulo) {
        modulosContratados.add(plazaModulo);
        plazaModulo.setPlaza(this);
    }

    public void removerModulo(PlazaModulo plazaModulo) {
        modulosContratados.remove(plazaModulo);
        plazaModulo.setPlaza(null);
    }

    public void agregarNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
        notificacion.setPlaza(this);
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}