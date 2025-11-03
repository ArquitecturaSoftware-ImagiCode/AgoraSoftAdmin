package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "plaza_instances")
public class PlazaInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK a la plaza en Admin DB (puede ser Long o String según tu modelo)
    @Column(name = "plaza_id", nullable = false)
    private Long plazaId;

    @Column(name = "base_url", nullable = false)
    private String baseUrl; // e.g. http://plaza-instance-host:8085

    @Column(name = "active", nullable = false)
    private boolean active = true;

    // getters / setters
    public Long getId() {
        return id;
    }

    public Long getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(Long plazaId) {
        this.plazaId = plazaId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}