package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id", nullable = false)
    private Plaza plaza;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean leida;

    @Column
    private LocalDateTime fechaLectura;

    // Empleado que envió la notificación (si fue manual)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enviado_por_empleado_id")
    private Empleado enviadoPor;

    // Constructores
    public Notificacion() {
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }

    public Notificacion(Plaza plaza, String titulo, String mensaje,
            TipoNotificacion tipo, Empleado enviadoPor) {
        this();
        this.plaza = plaza;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.enviadoPor = enviadoPor;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(LocalDateTime fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public Empleado getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(Empleado enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    // Método auxiliar
    public void marcarComoLeida() {
        this.leida = true;
        this.fechaLectura = LocalDateTime.now();
    }
}