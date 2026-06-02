package com.metodologia.bodyPaint.feature.dtos.request;

import lombok.Data;

@Data
public class AplicarCuponRequest {

    private String emailCliente;
    private String codigoCupon;
    private Double total;
}