package com.metodologia.bodyPaint.feature.controllers.post;

import java.security.Principal;


import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/mio")
    public BaseResponse<Carrito> obtener(Principal principal){
        return BaseResponse.ok(
                carritoService.obtener(principal.getName()),
                "Carrito del cliente");
    }

    @PostMapping("/agregar")
    public BaseResponse<Carrito> agregar(
            @RequestBody AgregarProductoCarritoRequest request,
            Principal principal) {

        return BaseResponse.ok(
                carritoService.agregarProducto(
                        principal.getName(),
                        request.getProductoId(),
                        request.getCantidad()),
                "Producto agregado");
    }

    @PutMapping("/modificar")
    public BaseResponse<Carrito> modificar(
            @RequestBody AgregarProductoCarritoRequest request,
            Principal principal) {

        return BaseResponse.ok(
                carritoService.modificarCantidad(
                        principal.getName(),
                        request.getProductoId(),
                        request.getCantidad()),
                "Cantidad modificada");
    }



}