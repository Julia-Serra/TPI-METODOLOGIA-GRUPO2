package com.metodologia.bodyPaint.feature.services.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IProductoGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoGetService implements IProductoGetService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listar() {
        // Cambiamos el findAll() por nuestro filtro de activos
        return productoRepository.findByActivoTrue();
    }
}