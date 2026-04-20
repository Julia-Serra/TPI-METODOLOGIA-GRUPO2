package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ClienteRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @Email
    private String email;

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
}
