package com.metodologia.bodyPaint.feature.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ProductoRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String marca;

    @Positive
    private double precio;

    @PositiveOrZero
    private int stock;

    private String imagen;
}
