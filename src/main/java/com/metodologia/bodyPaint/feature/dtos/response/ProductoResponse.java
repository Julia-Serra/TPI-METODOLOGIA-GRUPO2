package com.metodologia.bodyPaint.feature.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String marca;
    private double precio;
    private int stock;
    private String imagen;
}