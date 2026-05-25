package com.metodologia.bodyPaint.feature.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CancelarPedidoRequest {
    private String motivo;
}
