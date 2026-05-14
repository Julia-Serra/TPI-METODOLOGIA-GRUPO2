package com.metodologia.bodyPaint.feature.controllers.delete;

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

    @DeleteMapping("/{id}/vaciar")
    public BaseResponse<Carrito> vaciar(@PathVariable Long id) {

        Carrito carrito = carritoService.vaciar(id);

        return BaseResponse.ok(carrito, "Carrito vaciado");
    }
}