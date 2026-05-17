package com.metodologia.bodyPaint.feature.controllers.post;
import com.metodologia.bodyPaint.feature.dtos.request.ConfirmarPedidoRequest;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IPedidoConfirmService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor

public class PedidoPostController {
    private final IPedidoConfirmService pedidoConfirmService;

    @PostMapping("/confirmar")
    public Pedido confirmarPedido(
            @RequestBody ConfirmarPedidoRequest request
    ) {

        return pedidoConfirmService.confirmarPedido(request);
    }
}
