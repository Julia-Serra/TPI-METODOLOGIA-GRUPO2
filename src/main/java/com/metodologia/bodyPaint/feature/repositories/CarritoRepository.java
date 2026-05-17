package com.metodologia.bodyPaint.feature.repositories;

import com.metodologia.bodyPaint.feature.models.Carrito;
import com.metodologia.bodyPaint.feature.models.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    Optional<Carrito> findByCliente(Cliente cliente);

}