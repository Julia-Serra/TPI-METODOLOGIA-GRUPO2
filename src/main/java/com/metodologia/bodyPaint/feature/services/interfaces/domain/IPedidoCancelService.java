package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.request.CancelarPedidoRequest;

public interface IPedidoCancelService {
    
    void cancelarPedido(Long pedidoId, CancelarPedidoRequest request);
}
