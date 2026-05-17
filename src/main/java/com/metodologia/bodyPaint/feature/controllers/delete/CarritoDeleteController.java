package com.metodologia.bodyPaint.feature.controllers.delete;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.services.impl.domain.CarritoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carritos")
@RequiredArgsConstructor
public class CarritoDeleteController {

    private final CarritoService carritoService;

    @DeleteMapping("/vaciar")
    public BaseResponse<Carrito> vaciar(Authentication auth) {

        Carrito carrito = carritoService.vaciar(auth.getName());

        return BaseResponse.ok(carrito, "Carrito vaciado");
    }

    @DeleteMapping("/quitar/{productoId}")
    public BaseResponse<Carrito> quitar(
            @PathVariable Long productoId,
            Authentication auth
    ) {

        Carrito carrito = carritoService.quitarProducto(
                auth.getName(),
                productoId
        );

        return BaseResponse.ok(carrito, "Producto eliminado del carrito");
    }
}