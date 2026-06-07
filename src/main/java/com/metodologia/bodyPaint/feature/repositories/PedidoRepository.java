package com.metodologia.bodyPaint.feature.repositories;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.dtos.response.ReporteProductoMasVendidoResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByEstadoAndCliente(EstadoPedido estado, Cliente cliente);

    // --- CONSULTA PARA LA NUEVA US: REPORTE DE MÁS VENDIDOS ---
    @Query("SELECT new com.metodologia.bodyPaint.feature.dtos.response.ReporteProductoMasVendidoResponse(" +
           "p.id, p.nombre, p.marca, SUM(i.cantidad)) " +
           "FROM Pedido ped " +
           "JOIN ped.items i " +
           "JOIN i.producto p " +
           "WHERE ped.estado IN (com.metodologia.bodyPaint.feature.models.EstadoPedido.LISTO, " +
           "com.metodologia.bodyPaint.feature.models.EstadoPedido.RETIRADO_POR_CORREO, " +
           "com.metodologia.bodyPaint.feature.models.EstadoPedido.ENTREGADO) " +
           "AND (:mes IS NULL OR MONTH(ped.fecha) = :mes) " +
           "AND (:anio IS NULL OR YEAR(ped.fecha) = :anio) " +
           "GROUP BY p.id, p.nombre, p.marca " +
           "ORDER BY SUM(i.cantidad) DESC")
    List<ReporteProductoMasVendidoResponse> obtenerProductosMasVendidos(
            @Param("mes") Integer mes,
            @Param("anio") Integer anio
    );
    List<Pedido> findByClienteOrderByFechaDesc(Cliente cliente); //US nueva------
}