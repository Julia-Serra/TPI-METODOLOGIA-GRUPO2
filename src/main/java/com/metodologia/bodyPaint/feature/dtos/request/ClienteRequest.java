package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ClienteRequest {

    @NotBlank(message = "Por favor, ingrese un nombre")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "Por favor, ingrese un apellido")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
    private String apellido;

    @Email(message = "Por favor ingrese un email valido")
    private String email;

    @NotBlank(message = "Por favor ingrese un pais")
    private String pais;

    @NotBlank(message = "Por favor ingrese una provincia")
    private String provincia;

    @NotBlank(message = "Por favor ingrese una lovalidad")
    private String localidad;

    @NotBlank(message = "Por favor ingrese una calle")
    private String calle;

    @NotBlank(message = "Por favor ingrese el numero de la calle")
    private String numero;

    private String piso;
    private String departamento;
}
