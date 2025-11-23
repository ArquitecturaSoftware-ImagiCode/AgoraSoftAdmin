package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imagicode.agorasoftadmin.entidades.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    // Buscar por StripeSubscriptionId
    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);

    // Buscar por StripeCustomerId
    @Query("SELECT s FROM Subscription s WHERE s.stripeCustomerId = :stripeCustomerId")
    List<Subscription> findByStripeCustomerId(@Param("stripeCustomerId") String stripeCustomerId);

    // Buscar por PriceId
    @Query("SELECT s FROM Subscription s WHERE s.priceId = :priceId")
    List<Subscription> findByPriceId(@Param("priceId") String priceId);

    // Buscar por Status
    @Query("SELECT s FROM Subscription s WHERE s.status = :status")
    List<Subscription> findByStatus(@Param("status") String status);

    // Buscar por OrganizationId
    @Query("SELECT s FROM Subscription s WHERE s.organizationId = :organizationId")
    List<Subscription> findByOrganizationId(@Param("organizationId") String organizationId);

    // Buscar suscripciones activas
    @Query("SELECT s FROM Subscription s WHERE s.status = 'active'")
    List<Subscription> findActiveSubscriptions();

    // Buscar suscripciones canceladas
    @Query("SELECT s FROM Subscription s WHERE s.status = 'canceled'")
    List<Subscription> findCanceledSubscriptions();

    // Buscar suscripciones que expiran pronto (próximos 30 días)
    @Query("SELECT s FROM Subscription s WHERE s.currentPeriodEnd BETWEEN CURRENT_TIMESTAMP AND CURRENT_TIMESTAMP + 30 DAY")
    List<Subscription> findSubscriptionsExpiringSoon();

    // Verificar si existe por StripeSubscriptionId
    boolean existsByStripeSubscriptionId(String stripeSubscriptionId);

    // Verificar si existe por OrganizationId
    boolean existsByOrganizationId(String organizationId);
}
