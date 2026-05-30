package com.metodologia.bodyPaint.feature.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProductoMasVendidoResponse {
    private Long productoId;
    private String nombre;
    private String marca;
    private Long cantidadTotalVendida;
}