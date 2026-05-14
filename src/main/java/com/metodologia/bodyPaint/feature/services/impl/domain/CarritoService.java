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

        // validar stock
        if(producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        // buscar si ya existe el producto en el carrito
        ItemCarrito existente = carrito.getItems()
                .stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        // si existe, suma cantidad
        if(existente != null) {

            int nuevaCantidad = existente.getCantidad() + cantidad;

            if(producto.getStock() < nuevaCantidad) {
                throw new RuntimeException("Stock insuficiente");
            }

            existente.setCantidad(nuevaCantidad);

        } else {

            ItemCarrito item = ItemCarrito.builder()
                    .producto(producto)
                    .cantidad(cantidad)
                    .build();

            carrito.getItems().add(item);
        }

        return carritoRepository.save(carrito);
    }

    public Carrito vaciar(Long carritoId) {

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        carrito.getItems().clear();

        return carritoRepository.save(carrito);
    }
    public Carrito modificarCantidad(
            Long carritoId,
            Long productoId,
            int cantidad
    ) {

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        ItemCarrito item = carrito.getItems()
                .stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow();

        if(item.getProducto().getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        item.setCantidad(cantidad);

        return carritoRepository.save(carrito);
    }
    public Carrito quitarProducto(Long carritoId, Long productoId) {

        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow();

        carrito.getItems().removeIf(
                i -> i.getProducto().getId().equals(productoId)
        );

        return carritoRepository.save(carrito);
    }
}