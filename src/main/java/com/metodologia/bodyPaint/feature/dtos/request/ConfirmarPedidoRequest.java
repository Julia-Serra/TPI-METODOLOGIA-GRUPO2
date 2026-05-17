package com.metodologia.bodyPaint.feature.dtos.request;
import com.metodologia.bodyPaint.feature.models.Direccion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmarPedidoRequest {
    private Long carritoId;

    private String emailCliente;

    private Direccion domicilioEnvio;

    private String formaPago;
}
