package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IProductoCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoCreateService implements IProductoCreateService {

    private final ProductoRepository productoRepository;

    @Override
    public void crear(Producto producto) {
        productoRepository.save(producto);
    }
}
