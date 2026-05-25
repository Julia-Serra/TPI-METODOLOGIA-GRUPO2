package com.metodologia.bodyPaint.feature.models;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "pedido"
    )
    @Default
    private List <ItemCarrito> items = new ArrayList<>();

    @Embedded
    private Direccion domicilioEnvio;

    private String formaPago;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private String motivoCancelacion;

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
