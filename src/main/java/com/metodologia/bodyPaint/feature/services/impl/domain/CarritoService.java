package com.metodologia.bodyPaint.feature.services.impl.domain;

import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.ItemCarrito;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.repositories.CarritoRepository;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository; // <-- FALTABA

    // ================= OBTENER O CREAR CARRITO DEL CLIENTE =================
    private Carrito obtenerCarritoDelCliente(String email) {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado"));

        return carritoRepository.findByCliente(cliente)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setCliente(cliente);
                    return carritoRepository.save(nuevo);
                });
    }

    // ================= AGREGAR PRODUCTO =================
    public Carrito agregarProducto(String email, Long productoId, int cantidad) {

        if (cantidad <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        Carrito carrito = obtenerCarritoDelCliente(email);

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow();

        if (producto.getStock() < cantidad) {
            throw new BadRequestException("Stock insuficiente");
        }

        ItemCarrito existente = carrito.getItems()
                .stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (existente != null) {
            int nuevaCantidad = existente.getCantidad() + cantidad;

            if (producto.getStock() < nuevaCantidad) {
                throw new BadRequestException("Stock insuficiente");
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

    // ================= MODIFICAR CANTIDAD =================
    public Carrito modificarCantidad(String email, Long productoId, int cantidad) {

        Carrito carrito = obtenerCarritoDelCliente(email);

        ItemCarrito item = carrito.getItems()
                .stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Producto no encontrado"));

        if (item.getProducto().getStock() < cantidad) {
            throw new BadRequestException("Stock insuficiente");
        }

        item.setCantidad(cantidad);

        return carritoRepository.save(carrito);
    }

    // ================= QUITAR PRODUCTO =================
    public Carrito quitarProducto(String email, Long productoId) {

        Carrito carrito = obtenerCarritoDelCliente(email);

        boolean eliminado = carrito.getItems()
                .removeIf(i -> i.getProducto().getId().equals(productoId));

        if (!eliminado) {
            throw new BadRequestException("Producto no encontrado en carrito");
        }

        return carritoRepository.save(carrito);
    }

    // ================= VACIAR =================
    public Carrito vaciar(String email) {

        Carrito carrito = obtenerCarritoDelCliente(email);
        carrito.getItems().clear();

        return carritoRepository.save(carrito);
    }

    // ================= OBTENER =================
    public Carrito obtener(String email) {
        return obtenerCarritoDelCliente(email);
    }
}