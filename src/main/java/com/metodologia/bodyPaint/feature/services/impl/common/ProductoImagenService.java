package com.metodologia.bodyPaint.feature.services.impl.common;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.ImportarImagenRequest;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.common.IProductoImagenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoImagenService implements IProductoImagenService {

    private final ProductoRepository productoRepository;

    @Override
    public void importarImagen(Long productoId, ImportarImagenRequest request) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new BadRequestException("Producto no encontrado"));

        if (producto.getImagen() != null) {
            throw new BadRequestException("El producto ya posee una imagen asociada");
        }

        if (request.getArchivo() == null || request.getArchivo().isEmpty()) {
            throw new BadRequestException("Debe enviar una imagen");
        }

        String nombreArchivo = request.getArchivo().getOriginalFilename();

        producto.setImagen(nombreArchivo);

        productoRepository.save(producto);
    }
}