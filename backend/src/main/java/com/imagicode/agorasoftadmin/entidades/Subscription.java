package com.imagicode.agorasoftadmin.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Subscriptions\"")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;

    @Column(name = "\"StripeSubscriptionId\"", nullable = false)
    private String stripeSubscriptionId;

    @Column(name = "\"StripeCustomerId\"", nullable = false)
    private String stripeCustomerId;

    @Column(name = "\"PriceId\"", nullable = false)
    private String priceId;

    @Column(name = "\"Status\"", nullable = false)
    private String status;

    @Column(name = "\"CurrentPeriodStart\"", nullable = false)
    private LocalDateTime currentPeriodStart;

    @Column(name = "\"CurrentPeriodEnd\"", nullable = false)
    private LocalDateTime currentPeriodEnd;

    @Column(name = "\"CanceledAt\"")
    private LocalDateTime canceledAt;

    // Nota: OrganizationId en la BD es VARCHAR, no FK (puede ser ClerkOrgId)
    @Column(name = "\"OrganizationId\"", nullable = false)
    private String organizationId;

    @Column(name = "\"CreatedAt\"", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;

    public Subscription() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStripeSubscriptionId() { return stripeSubscriptionId; }
    public void setStripeSubscriptionId(String stripeSubscriptionId) { this.stripeSubscriptionId = stripeSubscriptionId; }

    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String stripeCustomerId) { this.stripeCustomerId = stripeCustomerId; }

    public String getPriceId() { return priceId; }
    public void setPriceId(String priceId) { this.priceId = priceId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCurrentPeriodStart() { return currentPeriodStart; }
    public void setCurrentPeriodStart(LocalDateTime currentPeriodStart) { this.currentPeriodStart = currentPeriodStart; }

    public LocalDateTime getCurrentPeriodEnd() { return currentPeriodEnd; }
    public void setCurrentPeriodEnd(LocalDateTime currentPeriodEnd) { this.currentPeriodEnd = currentPeriodEnd; }

    public LocalDateTime getCanceledAt() { return canceledAt; }
    public void setCanceledAt(LocalDateTime canceledAt) { this.canceledAt = canceledAt; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
