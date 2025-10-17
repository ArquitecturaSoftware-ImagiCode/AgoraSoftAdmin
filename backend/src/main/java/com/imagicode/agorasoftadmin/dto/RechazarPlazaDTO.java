package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RechazarPlazaDTO {

    @NotBlank(message = "El motivo del rechazo es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;

    // Constructores
    public RechazarPlazaDTO() {
    }

    public RechazarPlazaDTO(String motivo) {
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