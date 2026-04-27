package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ProductoRequest {

    @NotBlank(message = "Por favor, ingrese un nombre")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "Por favor, ingrese una marca")
    @Size(min = 1, max = 50, message = "La marca debe tener entre 1 y 50 caracteres")
    private String marca;

    @Positive(message = "El precio debe ser mayor a 0)")
    private double precio;

    @PositiveOrZero(message = "El stock debe ser mayor o igual a 0")
    private int stock;

    private String imagen;
}
