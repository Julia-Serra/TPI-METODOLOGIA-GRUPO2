package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.services.impl.domain.PedidoGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoGetController {

    private final PedidoGetService pedidoGetService;

    @GetMapping("/pendientes")
    public BaseResponse<List<Pedido>> listarPendientes() {
        List<Pedido> pedidos = pedidoGetService.listarPendientes();
        return BaseResponse.ok(pedidos, "Pedidos pendientes");
    }
}
