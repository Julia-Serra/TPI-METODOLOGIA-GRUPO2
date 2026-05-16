package com.metodologia.bodyPaint.feature.models;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @Embedded
    private Direccion domicilioEnvio;

    private String formaPago;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private String motivoCancelacion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemCarrito> items = new ArrayList<>();

    public void cancelar(String motivo) {

        if (motivo == null || motivo.isBlank()) {
            throw new BadRequestException("Debe informar el motivo de cancelación");
        }

        if (this.estado == EstadoPedido.RETIRADO_POR_CORREO
                || this.estado == EstadoPedido.ENTREGADO) {
            throw new BadRequestException("No se puede cancelar un pedido ya notificado al proveedor");
        }

        this.estado = EstadoPedido.CANCELADO;
        this.motivoCancelacion = motivo;
    }

    public void actualizarEstado(EstadoPedido nuevoEstado) {
        if (this.estado == EstadoPedido.CANCELADO) {
            throw new BadRequestException("No se puede modificar el estado de un pedido CANCELADO");
        }
        this.estado = nuevoEstado;
    }
}
