package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.specifications.ProductoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoFiltroService {

    private final ProductoRepository productoRepository;

    public List<Producto> filtrar(
            String nombre,
            String marca,
            Double precioMin,
            Double precioMax,
            Integer stockMin,
            Integer stockMax
    ) {

        Specification<Producto> spec = Specification
                .where(ProductoSpecification.nombreContiene(nombre))
                .and(ProductoSpecification.marcaContiene(marca))
                .and(ProductoSpecification.precioMayorIgual(precioMin))
                .and(ProductoSpecification.precioMenorIgual(precioMax))
                .and(ProductoSpecification.stockMayorIgual(stockMin))
                .and(ProductoSpecification.stockMenorIgual(stockMax))
                .and(ProductoSpecification.datosCompletos());

        return productoRepository.findAll(spec);
    }
}