package com.metodologia.bodyPaint.feature.mappers;

import com.metodologia.bodyPaint.feature.dtos.request.ProductoRequest;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.models.Producto;

public class ProductoMapper {

    private ProductoMapper(){}

    public static Producto toEntity(ProductoRequest request){
        return Producto.builder()
                .nombre(request.getNombre())
                .marca(request.getMarca())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .imagen(request.getImagen())
                .build();
    }

    public static ProductoResponse toResponse(Producto producto){
        // Calculamos la alerta: si tiene stock mínimo configurado y el stock actual es menor o igual a ese límite
        boolean mostrarAlerta = producto.getStockMinimo() != null && producto.getStock() <= producto.getStockMinimo();

        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .marca(producto.getMarca())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .imagen(producto.getImagen())
                .stockMinimo(producto.getStockMinimo()) // nuevo campo
                .alertaStockMinimo(mostrarAlerta)       // boolean para advertencia
                .build();
    }
}