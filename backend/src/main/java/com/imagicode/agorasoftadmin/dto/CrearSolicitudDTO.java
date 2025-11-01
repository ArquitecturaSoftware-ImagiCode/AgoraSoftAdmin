package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CrearSolicitudDTO {

    @NotBlank
    private String plazaNombre;

    @NotBlank
    private String representante;

    @NotBlank
    @Email
    private String correo;

    private String telefono;

    private String organizacion;

    private String rol;

    public CrearSolicitudDTO() {
    }

    public String getPlazaNombre() {
        return plazaNombre;
    }

    public void setPlazaNombre(String plazaNombre) {
        this.plazaNombre = plazaNombre;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
