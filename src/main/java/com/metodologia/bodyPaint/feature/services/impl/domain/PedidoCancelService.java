package com.metodologia.bodyPaint.feature.services.impl.domain;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.CancelarPedidoRequest;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IPedidoCancelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoCancelService implements IPedidoCancelService {

    private final PedidoRepository pedidoRepository;

    @Override
    public void cancelarPedido(Long pedidoId, CancelarPedidoRequest request) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BadRequestException("Pedido no encontrado"));

        pedido.cancelar(request.getMotivo());

        pedidoRepository.save(pedido);
    }
}