package com.metodologia.bodyPaint.feature.repositories;

import com.metodologia.bodyPaint.feature.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}