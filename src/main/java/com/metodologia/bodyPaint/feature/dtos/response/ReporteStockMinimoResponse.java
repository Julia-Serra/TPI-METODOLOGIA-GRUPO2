package com.metodologia.bodyPaint.feature.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReporteStockMinimoResponse {
    private Long id;
    private String nombre;
    private Integer stockActual;
    private Integer stockMinimo;
    private String estado; // CRITICO, BAJO, PROXIMO_A_MINIMO
}
