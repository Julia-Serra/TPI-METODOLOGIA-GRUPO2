package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.request.ActualizarEstadoPedidoRequest;

public interface IActualizarPedidoService {

    void actualizarEstado(Long pedidoId, ActualizarEstadoPedidoRequest request);
}
