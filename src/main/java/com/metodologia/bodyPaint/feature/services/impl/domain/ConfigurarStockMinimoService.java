package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.dtos.request.ConfigurarStockMinimoRequest;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.mappers.ProductoMapper;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IConfigurarStockMinimoService;
import com.metodologia.bodyPaint.config.exceptions.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ConfigurarStockMinimoService implements IConfigurarStockMinimoService {

    private final ProductoRepository productoRepository;

    public ConfigurarStockMinimoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public ProductoResponse ejecutar(Long id, ConfigurarStockMinimoRequest request) {
        // Buscamos el producto. Si no existe, lanzamos la excepción estructurada.
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND, List.of("No existe un producto registrado con el ID: " + id)) {
                });

        // Asignamos el stock mínimo configurado
        producto.setStockMinimo(request.getStockMinimo());

        // Guardamos en la base de datos
        Producto productoActualizado = productoRepository.save(producto);

        // Mapeamos a la respuesta esperada
        return ProductoMapper.toResponse(productoActualizado);
    }
}