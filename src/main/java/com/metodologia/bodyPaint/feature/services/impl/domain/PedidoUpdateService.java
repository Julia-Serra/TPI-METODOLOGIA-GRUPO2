package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IPedidoUpdateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoUpdateService implements IPedidoUpdateService {

    private final PedidoRepository pedidoRepository;

    @Override
    public void actualizarEstado(Long idPedido, String estado) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        if (estado.equalsIgnoreCase("CANCELADO")) {
            throw new RuntimeException("No se permite cancelar pedidos desde esta operación");
        }

        EstadoPedido nuevoEstado = EstadoPedido.valueOf(estado.toUpperCase());

        pedido.setEstado(nuevoEstado);

        pedidoRepository.save(pedido);
    }
}