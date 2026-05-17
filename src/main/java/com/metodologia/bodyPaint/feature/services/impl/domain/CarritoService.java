package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
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
        if(cantidad <= 0) {
            throw new BadRequestException(
                    "La cantidad debe ser mayor a 0"
            );
        }

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    return carritoRepository.save(nuevo);
                });

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

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new BadRequestException("Carrito no encontrado"));

        carrito.getItems().clear();

        return carritoRepository.save(carrito);
    }
    public Carrito modificarCantidad(
            Long carritoId,
            Long productoId,
            int cantidad
    ) {

        if(cantidad <= 0) {
            throw new BadRequestException(
                    "La cantidad debe ser mayor a 0"
            );
        }

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new BadRequestException("Carrito no encontrado"));

        ItemCarrito item = carrito.getItems()
                .stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException("Producto no encontrado en carrito"));

        if(item.getProducto().getStock() < cantidad) {
            throw new BadRequestException("Stock insuficiente");
        }

        item.setCantidad(cantidad);

        return carritoRepository.save(carrito);
    }
    public Carrito quitarProducto(Long carritoId, Long productoId) {

        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new BadRequestException("Carrito no encontrado"));

        boolean eliminado = carrito.getItems().removeIf(
                i -> i.getProducto().getId().equals(productoId)
        );

        if(!eliminado) {
            throw new BadRequestException(
                    "Producto no encontrado en carrito"
            );
        }

        return carritoRepository.save(carrito);
    }
}