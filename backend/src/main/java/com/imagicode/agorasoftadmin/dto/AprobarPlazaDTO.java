package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AprobarPlazaDTO {

    @NotBlank(message = "El plan inicial es obligatorio")
    private String planInicial;

    @NotNull(message = "El monto mensual es obligatorio")
    private BigDecimal montoMensual;

    private String metodoPago;

    // Constructores
    public AprobarPlazaDTO() {
    }

    public AprobarPlazaDTO(String planInicial, BigDecimal montoMensual, String metodoPago) {
        this.planInicial = planInicial;
        this.montoMensual = montoMensual;
        this.metodoPago = metodoPago;
    }

    // Getters y Setters
    public String getPlanInicial() {
        return planInicial;
    }

    public void setPlanInicial(String planInicial) {
        this.planInicial = planInicial;
    }

    public BigDecimal getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(BigDecimal montoMensual) {
        this.montoMensual = montoMensual;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}