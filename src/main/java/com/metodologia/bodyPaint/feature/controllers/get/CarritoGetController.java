package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.repositories.CarritoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carritos")
@RequiredArgsConstructor
public class CarritoGetController {

    private final CarritoRepository carritoRepository;

    @GetMapping("/{id}")
    public BaseResponse<Carrito> obtener(@PathVariable Long id) {

        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Carrito no encontrado"));

        return BaseResponse.ok(carrito, "Carrito encontrado");
    }
}