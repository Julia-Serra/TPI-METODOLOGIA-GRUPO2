package com.metodologia.bodyPaint.feature.controllers.get;

import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.mappers.ProductoMapper;
import com.metodologia.bodyPaint.feature.services.impl.domain.ProductoFiltroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoFiltroController {

    private final ProductoFiltroService productoFiltroService;

    @GetMapping("/buscar")
    public List<ProductoResponse> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Integer stockMin,
            @RequestParam(required = false) Integer stockMax
    ) {

        List<Producto> productos = productoFiltroService.filtrar(
                nombre,
                marca,
                precioMin,
                precioMax,
                stockMin,
                stockMax
        );

        return productos.stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }
}
