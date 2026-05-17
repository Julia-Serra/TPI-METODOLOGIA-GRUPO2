package com.metodologia.bodyPaint.feature.services.interfaces.domain;
import com.metodologia.bodyPaint.feature.dtos.request.ConfirmarPedidoRequest;
import com.metodologia.bodyPaint.feature.models.Pedido;

public interface IPedidoConfirmService {
    Pedido confirmarPedido(ConfirmarPedidoRequest request);
}
