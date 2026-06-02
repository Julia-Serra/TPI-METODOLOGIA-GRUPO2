package com.metodologia.bodyPaint.feature.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AplicarCuponResponse {

    private String codigoCupon;

    private Double totalOriginal;

    private Double descuentoAplicado;

    private Double totalFinal;
}