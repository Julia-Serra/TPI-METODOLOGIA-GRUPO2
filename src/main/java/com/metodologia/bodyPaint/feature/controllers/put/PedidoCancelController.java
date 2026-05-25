package com.metodologia.bodyPaint.feature.controllers.put;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.CancelarPedidoRequest;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IPedidoCancelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoCancelController {

    private final IPedidoCancelService pedidoCancelService;

    @PutMapping("/{id}/cancelar")
    public BaseResponse<Void> cancelarPedido(@PathVariable Long id,
                               @RequestBody CancelarPedidoRequest request) {

        pedidoCancelService.cancelarPedido(id, request);
        return BaseResponse.ok(null, "Pedido cancelado correctamente");
    }
}