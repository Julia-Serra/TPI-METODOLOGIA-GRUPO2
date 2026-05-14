package com.metodologia.bodyPaint.feature.services.impl.domain;

import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.models.ItemCarrito;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.CarritoRepository;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public Carrito crear() {
        return carritoRepository.save(new Carrito());
    }

    public Carrito agregarProducto(Long carritoId, Long productoId, int cantidad) {

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        Producto producto = productoRepository.findById(productoId).orElseThrow();

        ItemCarrito item = ItemCarrito.builder()
                .producto(producto)
                .cantidad(cantidad)
                .build();

        carrito.getItems().add(item);

        return carritoRepository.save(carrito);
    }

    public Carrito vaciar(Long carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        carrito.getItems().clear();

        return carritoRepository.save(carrito);
    }
}