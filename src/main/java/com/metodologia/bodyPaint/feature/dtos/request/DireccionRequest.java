package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DireccionRequest {

    @NotBlank
    private String pais;

    @NotBlank
    private String provincia;

    @NotBlank
    private String localidad;

    @NotBlank
    private String calle;

    @NotBlank
    private String numero;

    private String piso;
    private String departamento;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}