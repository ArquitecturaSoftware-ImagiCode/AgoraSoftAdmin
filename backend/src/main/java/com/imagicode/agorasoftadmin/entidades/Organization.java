package com.imagicode.agorasoftadmin.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"Organizations\"")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Integer id;

    @Column(name = "\"ClerkOrgId\"", nullable = false)
    private String clerkOrgId;

    @Column(name = "\"Name\"", nullable = false)
    private String name;

    @Column(name = "\"OwnerId\"", nullable = false)
    private Integer ownerId;

    @Column(name = "\"IsActive\"", nullable = false)
    private Boolean isActive = false;

    @Column(name = "\"CreatedAt\"", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;

    public Organization() {
    }

    public Organization(String clerkOrgId, String name, Integer ownerId, Boolean isActive) {
        this.clerkOrgId = clerkOrgId;
        this.name = name;
        this.ownerId = ownerId;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClerkOrgId() {
        return clerkOrgId;
    }

    public void setClerkOrgId(String clerkOrgId) {
        this.clerkOrgId = clerkOrgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
