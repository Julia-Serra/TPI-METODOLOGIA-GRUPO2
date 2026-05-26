package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.feature.dtos.response.ReporteStockMinimoResponse;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IReporteStockMinimoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReporteStockMinimoService implements IReporteStockMinimoService {

    private final ProductoRepository productoRepository;

    public ReporteStockMinimoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ReporteStockMinimoResponse> obtenerProductosCercaAlMinimo(double porcentajeAlerta) {
        List<Producto> productos = productoRepository.findByActivoTrue();

        return productos.stream()
                .filter(p -> p.getStockMinimo() != null)
                .filter(p -> p.getStock() <= (p.getStockMinimo() * porcentajeAlerta / 100.0))
                .map(p -> mapearAlReporte(p))
                .sorted((a, b) -> a.getEstado().compareTo(b.getEstado()))
                .toList();
    }

    private ReporteStockMinimoResponse mapearAlReporte(Producto producto) {
        String estado = determinarEstado(producto);

        return ReporteStockMinimoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stockActual(producto.getStock())
                .stockMinimo(producto.getStockMinimo())
                .estado(estado)
                .build();
    }

    private String determinarEstado(Producto producto) {
        if (producto.getStock() < producto.getStockMinimo()) {
            return "CRITICO";
        } else if (producto.getStock() <= (producto.getStockMinimo() * 1.2)) {
            return "BAJO";
        } else {
            return "PROXIMO_A_MINIMO";
        }
    }
}
