package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClienteRequest {

    @NotBlank(message = "Por favor, ingrese un nombre")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "El nombre solo puede contener letras"
    )
    private String nombre;

    @NotBlank(message = "Por favor, ingrese un apellido")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "El apellido solo puede contener letras"
    )
    private String apellido;

    @Email(message = "Por favor ingrese un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "Por favor ingrese un país")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "El país solo puede contener letras"
    )
    private String pais;

    @NotBlank(message = "Por favor ingrese una provincia")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "La provincia solo puede contener letras"
    )
    private String provincia;

    @NotBlank(message = "Por favor ingrese una localidad")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$",
            message = "La localidad solo puede contener letras"
    )
    private String localidad;

    @NotBlank(message = "Por favor ingrese una calle")
    private String calle;

    @NotBlank(message = "Por favor ingrese el número de la calle")
    private String numero;

    private String piso;

    private String departamento;
}
