package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoGetService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public List<Pedido> listarPendientes() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE_PREPARACION)
                .stream()
                .filter(p -> p.getCliente() != null)
                .filter(p -> p.getItems() != null && !p.getItems().isEmpty())
                .toList();
    }

    public List<Pedido> listarPendientesDelCliente(String email) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmailIgnoreCase(email);
        if (clienteOpt.isEmpty()) {
            return List.of();
        }

        Cliente cliente = clienteOpt.get();
        return pedidoRepository.findByEstadoAndCliente(EstadoPedido.PENDIENTE_PREPARACION, cliente)
                .stream()
                .filter(p -> p.getItems() != null && !p.getItems().isEmpty())
                .toList();
    }
}
