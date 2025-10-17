package com.imagicode.agorasoftadmin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CrearPlazaDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 20, message = "El RUT no puede exceder 20 caracteres")
    private String rut;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefono;

    @NotBlank(message = "El email de contacto es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String emailContacto;

    @NotBlank(message = "El representante legal es obligatorio")
    @Size(max = 200, message = "El representante legal no puede exceder 200 caracteres")
    private String representanteLegal;

    // Constructores
    public CrearPlazaDTO() {
    }

    public CrearPlazaDTO(String nombre, String rut, String direccion, String telefono,
            String emailContacto, String representanteLegal) {
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.emailContacto = emailContacto;
        this.representanteLegal = representanteLegal;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }
}