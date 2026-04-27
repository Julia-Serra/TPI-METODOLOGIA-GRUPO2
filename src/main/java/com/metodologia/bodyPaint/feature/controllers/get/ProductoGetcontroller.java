package com.metodologia.bodyPaint.feature.controllers.get;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.mappers.ProductoMapper;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IProductoGetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor

public class ProductoGetcontroller {

    private final IProductoGetService productoGetService;

    @GetMapping
    public BaseResponse<List<ProductoResponse>> listar() {

        List<Producto> productos = productoGetService.listar();

        List<ProductoResponse> response = productos.stream()
                .map(ProductoMapper::toResponse)
                .toList();

        return BaseResponse.ok(response, "Lista de productos");
    }

}
