package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.ActualizarEstadoPedidoRequest;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IActualizarPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarPedidoService implements IActualizarPedidoService {

    private final PedidoRepository pedidoRepository;

    @Override
    public void actualizarEstado(Long pedidoId, ActualizarEstadoPedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BadRequestException("Pedido no encontrado"));

        pedido.actualizarEstado(request.getEstado());

        pedidoRepository.save(pedido);
    }
}
