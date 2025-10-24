package com.imagicode.agorasoftadmin.controladores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagicode.agorasoftadmin.entidades.Subscription;
import com.imagicode.agorasoftadmin.servicios.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    // GET: traer todas las suscripciones
    @GetMapping
    public List<Subscription> listarSuscripciones() {
        return subscriptionService.obtenerTodasLasSuscripciones();
    }

    // GET: traer suscripción por id
    @GetMapping("/{id}")
    public Optional<Subscription> obtenerSuscripcionPorId(@PathVariable Integer id) {
        return subscriptionService.obtenerSuscripcionPorId(id);
    }

    // GET: traer suscripción por StripeSubscriptionId
    @GetMapping("/stripe/{stripeSubscriptionId}")
    public Optional<Subscription> obtenerSuscripcionPorStripeId(@PathVariable String stripeSubscriptionId) {
        return subscriptionService.obtenerSuscripcionPorStripeId(stripeSubscriptionId);
    }

    // POST: crear suscripción
    @PostMapping
    public Subscription crearSuscripcion(@RequestBody Subscription subscription) {
        System.out.println("Creando suscripción: " + subscription);
        return subscriptionService.crearSuscripcion(subscription);
    }

    // PUT: actualizar suscripción
    @PutMapping("/{id}")
    public Subscription actualizarSuscripcion(@PathVariable Integer id, @RequestBody Subscription subscription) {
        subscription.setId(id);
        return subscriptionService.actualizarSuscripcion(subscription);
    }

    // DELETE: eliminar suscripción
    @DeleteMapping("/{id}")
    public void eliminarSuscripcion(@PathVariable Integer id) {
        subscriptionService.eliminarSuscripcion(id);
    }

    // GET: traer suscripciones por customer ID
    @GetMapping("/customer/{stripeCustomerId}")
    public List<Subscription> obtenerSuscripcionesPorCustomerId(@PathVariable String stripeCustomerId) {
        return subscriptionService.obtenerSuscripcionesPorCustomerId(stripeCustomerId);
    }

    // GET: traer suscripciones por price ID
    @GetMapping("/price/{priceId}")
    public List<Subscription> obtenerSuscripcionesPorPriceId(@PathVariable String priceId) {
        return subscriptionService.obtenerSuscripcionesPorPriceId(priceId);
    }

    // GET: traer suscripciones por estado
    @GetMapping("/status/{status}")
    public List<Subscription> obtenerSuscripcionesPorEstado(@PathVariable String status) {
        return subscriptionService.obtenerSuscripcionesPorEstado(status);
    }

    // GET: traer suscripciones por organización
    @GetMapping("/organization/{organizationId}")
    public List<Subscription> obtenerSuscripcionesPorOrganizacion(@PathVariable String organizationId) {
        return subscriptionService.obtenerSuscripcionesPorOrganizacion(organizationId);
    }

    // GET: traer suscripciones activas
    @GetMapping("/active")
    public List<Subscription> obtenerSuscripcionesActivas() {
        return subscriptionService.obtenerSuscripcionesActivas();
    }

    // GET: traer suscripciones canceladas
    @GetMapping("/canceled")
    public List<Subscription> obtenerSuscripcionesCanceladas() {
        return subscriptionService.obtenerSuscripcionesCanceladas();
    }

    // GET: traer suscripciones que expiran pronto
    @GetMapping("/expiring-soon")
    public List<Subscription> obtenerSuscripcionesQueExpiranPronto() {
        return subscriptionService.obtenerSuscripcionesQueExpiranPronto();
    }

    // GET: verificar si existe por StripeSubscriptionId
    @GetMapping("/exists/stripe/{stripeSubscriptionId}")
    public boolean existePorStripeSubscriptionId(@PathVariable String stripeSubscriptionId) {
        return subscriptionService.existePorStripeSubscriptionId(stripeSubscriptionId);
    }

    // GET: verificar si existe por OrganizationId
    @GetMapping("/exists/organization/{organizationId}")
    public boolean existePorOrganizationId(@PathVariable String organizationId) {
        return subscriptionService.existePorOrganizationId(organizationId);
    }

    // PUT: cancelar suscripción
    @PutMapping("/{id}/cancel")
    public Subscription cancelarSuscripcion(@PathVariable Integer id) {
        return subscriptionService.cancelarSuscripcion(id);
    }

    // PUT: activar suscripción
    @PutMapping("/{id}/activate")
    public Subscription activarSuscripcion(@PathVariable Integer id) {
        return subscriptionService.activarSuscripcion(id);
    }

    // PUT: renovar suscripción
    @PutMapping("/{id}/renew")
    public Subscription renovarSuscripcion(@PathVariable Integer id, 
                                         @RequestBody RenewalRequest renewalRequest) {
        return subscriptionService.renovarSuscripcion(id, 
                                                    renewalRequest.getNewPeriodStart(), 
                                                    renewalRequest.getNewPeriodEnd());
    }

    // Clase interna para el request de renovación
    public static class RenewalRequest {
        private LocalDateTime newPeriodStart;
        private LocalDateTime newPeriodEnd;

        public LocalDateTime getNewPeriodStart() {
            return newPeriodStart;
        }

        public void setNewPeriodStart(LocalDateTime newPeriodStart) {
            this.newPeriodStart = newPeriodStart;
        }

        public LocalDateTime getNewPeriodEnd() {
            return newPeriodEnd;
        }

        public void setNewPeriodEnd(LocalDateTime newPeriodEnd) {
            this.newPeriodEnd = newPeriodEnd;
        }
    }
}
