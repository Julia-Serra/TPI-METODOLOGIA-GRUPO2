package com.metodologia.bodyPaint.feature.controllers.post;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.ProductoRequest;
import com.metodologia.bodyPaint.feature.mappers.ProductoMapper;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IProductoCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoPostController {

    private final IProductoCreateService productoCreateService;

    @PostMapping
    public BaseResponse<Void> crear(@Valid @RequestBody ProductoRequest request) {

        Producto producto = ProductoMapper.toEntity(request);

        productoCreateService.crear(producto);

        return BaseResponse.ok(null, "Producto creado");
    }
}