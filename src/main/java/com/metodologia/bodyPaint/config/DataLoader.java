package com.metodologia.bodyPaint.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Direccion;
import com.metodologia.bodyPaint.feature.models.EstadoPedido;
import com.metodologia.bodyPaint.feature.models.ItemCarrito;
import com.metodologia.bodyPaint.feature.models.Pedido;
import com.metodologia.bodyPaint.feature.models.Producto;
import com.metodologia.bodyPaint.feature.models.Rol;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;
import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
import com.metodologia.bodyPaint.feature.repositories.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (clienteRepository.count() == 0) {

            clienteRepository.save(
                Cliente.builder()
                    .nombre("Admin")
                    .apellido("Principal")
                    .email("admin@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_ADMIN)
                    .build()
            );

            Direccion dir1 = Direccion.builder()
                    .calle("Av. Corrientes")
                    .numero("1234")
                    .localidad("CABA")
                    .build();

            Direccion dir2 = Direccion.builder()
                    .calle("Calle Falsa")
                    .numero("123")
                    .localidad("La Plata")
                    .build();

            Cliente cliente = clienteRepository.save(
                Cliente.builder()
                    .nombre("Cliente")
                    .apellido("Demo")
                    .email("cliente@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_CLIENTE)
                    .direcciones(Arrays.asList(dir1, dir2))
                    .build()
            );

            clienteRepository.save(
                Cliente.builder()
                    .nombre("Vendedor")
                    .apellido("BodyPaint")
                    .email("vendedor@bodypaint.com")
                    .password(passwordEncoder.encode("1234"))
                    .rol(Rol.ROLE_VENDEDOR)
                    .build()
            );

            
            Producto p1 = productoRepository.save(
                Producto.builder()
                    .nombre("Pintura Facial")
                    .marca("BodyPaint Pro")
                    .precio(150.00)
                    .stock(50)
                    .stockMinimo(10)
                    .activo(true)
                    .imagen("pintura-facial.jpg") 
                    .build()
            );

           
            Producto p2 = productoRepository.save(
                Producto.builder()
                    .nombre("Pintura Corporal")
                    .marca("BodyArt")
                    .precio(200.00)
                    .stock(30)
                    .stockMinimo(5)
                    .activo(true)
                    .imagen("pintura-corporal.jpg") 
                    .build()
            );

            
            Producto p3 = productoRepository.save(
                Producto.builder()
                    .nombre("Set de Brochas")
                    .marca("ProBrush")
                    .precio(80.00)
                    .stock(25)
                    .stockMinimo(8)
                    .activo(true)
                    .imagen("set-brochas.jpg") 
                    .build()
            );

            ItemCarrito item1 = ItemCarrito.builder()
                    .producto(p1)
                    .cantidad(2)
                    .build();

            ItemCarrito item2 = ItemCarrito.builder()
                    .producto(p2)
                    .cantidad(1)
                    .build();

            pedidoRepository.save(
                Pedido.builder()
                    .cliente(cliente)
                    .items(Arrays.asList(item1, item2))
                    .domicilioEnvio(dir1)
                    .formaPago("Tarjeta de Crédito")
                    .estado(EstadoPedido.PENDIENTE_PREPARACION)
                    .build()
            );
        }
    }
}