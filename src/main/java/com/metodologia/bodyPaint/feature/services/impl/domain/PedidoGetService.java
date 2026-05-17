package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoGetService {

    private final PedidoRepository pedidoRepository;

    public List<Pedido> listarPendientes() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE_PREPARACION)
                .stream()
                .filter(p -> p.getCliente() != null)
                .filter(p -> p.getItems() != null && !p.getItems().isEmpty())
                .toList();
    }
}
