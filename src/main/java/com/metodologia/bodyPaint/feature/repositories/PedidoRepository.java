package com.metodologia.bodyPaint.feature.repositories;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import com.metodologia.bodyPaint.feature.models.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByEstadoAndCliente(EstadoPedido estado, Cliente cliente);
}
