package com.metodologia.bodyPaint.feature.dtos.request;

import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import lombok.Data;

@Data
public class ActualizarEstadoPedidoRequest {
    private EstadoPedido estado;
}
