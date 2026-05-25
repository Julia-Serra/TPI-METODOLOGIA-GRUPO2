package com.metodologia.bodyPaint.feature.services.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.mappers.ProductoMapper;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IDarDeBajaProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DarDeBajaProductoService implements IDarDeBajaProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public ProductoResponse ejecutar(Long id) {
        // 1. Buscar el producto - Si no existe, usamos RuntimeException
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto con ID: " + id));

        // PRUEBA DE USUARIO 2: Si ya está de baja, falla
        if (!producto.isActivo()) {
            throw new RuntimeException("El producto ya se encuentra dado de baja.");
        }

        // PRUEBA DE USUARIO 3: Validación de Kit (Simulada)
        boolean estaEnKit = false; // Cambiar a true para probar que el error funciona en frontend
        if (estaEnKit) {
            throw new RuntimeException("No se puede dar de baja: el producto forma parte de un kit activo.");
        }

        // PRUEBA DE USUARIO 1: Pasa la baja lógica
        producto.setActivo(false);
        return ProductoMapper.toResponse(productoRepository.save(producto));
    }
}