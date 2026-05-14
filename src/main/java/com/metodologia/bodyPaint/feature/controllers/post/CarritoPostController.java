package com.metodologia.bodyPaint.feature.controllers.post;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.AgregarProductoCarritoRequest;
import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.services.impl.domain.CarritoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carritos")
@RequiredArgsConstructor
public class CarritoPostController {

    private final CarritoService carritoService;

    @PostMapping
    public BaseResponse<Carrito> crear() {

        Carrito carrito = carritoService.crear();

        return BaseResponse.ok(carrito, "Carrito creado");
    }

    @PostMapping("/{id}/agregar")
    public BaseResponse<Carrito> agregar(
            @PathVariable Long id,
            @RequestBody AgregarProductoCarritoRequest request
    ) {

        Carrito carrito = carritoService.agregarProducto(
                id,
                request.getProductoId(),
                request.getCantidad()
        );

        return BaseResponse.ok(carrito, "Producto agregado al carrito");
    }
}