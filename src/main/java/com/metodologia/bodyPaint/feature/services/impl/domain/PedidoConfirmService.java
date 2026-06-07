package com.metodologia.bodyPaint.feature.services.impl.domain;
import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
import com.metodologia.bodyPaint.feature.dtos.request.ConfirmarPedidoRequest;
import com.metodologia.bodyPaint.feature.models.*;
import com.metodologia.bodyPaint.feature.repositories.CarritoRepository;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;
import com.metodologia.bodyPaint.feature.services.interfaces.domain.IPedidoConfirmService;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor

public class PedidoConfirmService implements IPedidoConfirmService {
    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Pedido confirmarPedido(ConfirmarPedidoRequest request) {

        if(request.getEmailCliente() == null ||
                request.getEmailCliente().isBlank()) {

            throw new BadRequestException(
                    "Debe informar email"
            );
        }

        Cliente cliente = clienteRepository
                .findByEmailIgnoreCase(request.getEmailCliente())
                .orElseThrow(() ->
                        new BadRequestException("Cliente no encontrado"));

        Carrito carrito = carritoRepository
                .findById(request.getCarritoId())
                .orElseThrow(() ->
                        new BadRequestException("Carrito no encontrado"));

        if(carrito.getItems() == null ||
                carrito.getItems().isEmpty()) {
            throw new BadRequestException(
                    "El carrito no tiene productos"
            );
        }

        if(request.getDomicilioEnvio() == null) {

            throw new BadRequestException(
                    "Debe informar domicilio"
            );
        }

        if(request.getFormaPago() == null ||
                request.getFormaPago().isBlank()) {
            throw new BadRequestException(
                    "Debe informar forma de pago"
            );
        }

        // validar stock
        for(ItemCarrito item : carrito.getItems()) {

            Producto producto = item.getProducto();

            if(producto.getStock() < item.getCantidad()) {

                throw new BadRequestException(
                        "Stock insuficiente para: " + producto.getNombre()
                );
            }
        }
        //descontar stock
        for (ItemCarrito item : carrito.getItems()){
            Producto producto = item.getProducto();

            producto.setStock(
                    producto.getStock() - item.getCantidad()
            );
            productoRepository.save(producto);
        }
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .items(new java.util.ArrayList<>())
                .domicilioEnvio(request.getDomicilioEnvio())
                .formaPago(request.getFormaPago())
                .estado(EstadoPedido.PENDIENTE_PREPARACION)
                .fecha(LocalDate.now())//US nueva---------
                .build();

        for (ItemCarrito item : carrito.getItems()) {
            ItemCarrito nuevoItem = ItemCarrito.builder()
                    .producto(item.getProducto())
                    .cantidad(item.getCantidad())
                    .pedido(pedido)
                    .build();
            pedido.getItems().add(nuevoItem);
        }

        return pedidoRepository.save(pedido);
    }
}
