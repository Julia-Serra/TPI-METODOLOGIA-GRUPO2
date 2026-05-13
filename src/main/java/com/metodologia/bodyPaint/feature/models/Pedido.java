package com.metodologia.bodyPaint.feature.models;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;

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
}
