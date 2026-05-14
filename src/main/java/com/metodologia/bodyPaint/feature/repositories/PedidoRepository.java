package com.metodologia.bodyPaint.feature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metodologia.bodyPaint.feature.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
