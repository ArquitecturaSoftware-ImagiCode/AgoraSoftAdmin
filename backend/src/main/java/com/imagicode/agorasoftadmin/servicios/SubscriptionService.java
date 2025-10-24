package com.imagicode.agorasoftadmin.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.imagicode.agorasoftadmin.entidades.Subscription;
import com.imagicode.agorasoftadmin.repositorios.SubscriptionRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> obtenerTodasLasSuscripciones() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> obtenerSuscripcionPorId(Integer id) {
        return subscriptionRepository.findById(id);
    }

    public Optional<Subscription> obtenerSuscripcionPorStripeId(String stripeSubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(stripeSubscriptionId);
    }

    public Subscription crearSuscripcion(Subscription subscription) {
        subscription.setCreatedAt(LocalDateTime.now());
        return subscriptionRepository.save(subscription);
    }

    public Subscription actualizarSuscripcion(Subscription subscription) {
        subscription.setUpdatedAt(LocalDateTime.now());
        return subscriptionRepository.save(subscription);
    }

    public void eliminarSuscripcion(Integer id) {
        subscriptionRepository.deleteById(id);
    }

    public List<Subscription> obtenerSuscripcionesPorCustomerId(String stripeCustomerId) {
        return subscriptionRepository.findByStripeCustomerId(stripeCustomerId);
    }

    public List<Subscription> obtenerSuscripcionesPorPriceId(String priceId) {
        return subscriptionRepository.findByPriceId(priceId);
    }

    public List<Subscription> obtenerSuscripcionesPorEstado(String status) {
        return subscriptionRepository.findByStatus(status);
    }

    public List<Subscription> obtenerSuscripcionesPorOrganizacion(String organizationId) {
        return subscriptionRepository.findByOrganizationId(organizationId);
    }

    public List<Subscription> obtenerSuscripcionesActivas() {
        return subscriptionRepository.findActiveSubscriptions();
    }

    public List<Subscription> obtenerSuscripcionesCanceladas() {
        return subscriptionRepository.findCanceledSubscriptions();
    }

    public List<Subscription> obtenerSuscripcionesQueExpiranPronto() {
        return subscriptionRepository.findSubscriptionsExpiringSoon();
    }

    public boolean existePorStripeSubscriptionId(String stripeSubscriptionId) {
        return subscriptionRepository.existsByStripeSubscriptionId(stripeSubscriptionId);
    }

    public boolean existePorOrganizationId(String organizationId) {
        return subscriptionRepository.existsByOrganizationId(organizationId);
    }

    public Subscription cancelarSuscripcion(Integer id) {
        Optional<Subscription> subOpt = subscriptionRepository.findById(id);
        if (subOpt.isPresent()) {
            Subscription subscription = subOpt.get();
            subscription.setStatus("canceled");
            subscription.setCanceledAt(LocalDateTime.now());
            subscription.setUpdatedAt(LocalDateTime.now());
            return subscriptionRepository.save(subscription);
        }
        return null;
    }

    public Subscription activarSuscripcion(Integer id) {
        Optional<Subscription> subOpt = subscriptionRepository.findById(id);
        if (subOpt.isPresent()) {
            Subscription subscription = subOpt.get();
            subscription.setStatus("active");
            subscription.setCanceledAt(null);
            subscription.setUpdatedAt(LocalDateTime.now());
            return subscriptionRepository.save(subscription);
        }
        return null;
    }

    public Subscription renovarSuscripcion(Integer id, LocalDateTime newPeriodStart, LocalDateTime newPeriodEnd) {
        Optional<Subscription> subOpt = subscriptionRepository.findById(id);
        if (subOpt.isPresent()) {
            Subscription subscription = subOpt.get();
            subscription.setCurrentPeriodStart(newPeriodStart);
            subscription.setCurrentPeriodEnd(newPeriodEnd);
            subscription.setStatus("active");
            subscription.setCanceledAt(null);
            subscription.setUpdatedAt(LocalDateTime.now());
            return subscriptionRepository.save(subscription);
        }
        return null;
    }
}
