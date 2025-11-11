package com.imagicode.agorasoftadmin.entidades;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Users\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;

    @Column(name = "\"ClerkUserId\"", nullable = false)
    private String clerkUserId;

    @Column(name = "\"Email\"", nullable = false)
    private String email;

    @Column(name = "\"CreatedAt\"", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;

    // Constructor vacío
    public User() {}

    public User(String clerkUserId, String email) {
        this.clerkUserId = clerkUserId;
        this.email = email;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClerkUserId() { return clerkUserId; }
    public void setClerkUserId(String clerkUserId) { this.clerkUserId = clerkUserId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}