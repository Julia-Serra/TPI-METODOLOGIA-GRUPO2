package com.metodologia.bodyPaint.feature.controllers.put;

import com.metodologia.bodyPaint.feature.dtos.request.ActualizarEstadoPedidoRequest;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IActualizarPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class ActualizarPedidoController {

    private final IActualizarPedidoService actualizarPedidoService;

    @PutMapping("/{id}/estado")
    public void actualizarEstado(
            @PathVariable Long id,
            @RequestBody ActualizarEstadoPedidoRequest request) {
        actualizarPedidoService.actualizarEstado(id, request);
    }
}
