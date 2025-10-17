package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SuspenderPlazaDTO {

    @NotBlank(message = "El motivo de la suspensión es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;

    // Constructores
    public SuspenderPlazaDTO() {
    }

    public SuspenderPlazaDTO(String motivo) {
        this.motivo = motivo;
    }

    // Getters y Setters
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}