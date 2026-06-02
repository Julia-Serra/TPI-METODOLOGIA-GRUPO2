package com.metodologia.bodyPaint.feature.dtos;

import com.metodologia.bodyPaint.feature.models.CuponDescuento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuponDescuentoRepository
        extends JpaRepository<CuponDescuento, Long> {

    boolean existsByCodigo(String codigo);

    Optional<CuponDescuento> findByCodigo(String codigo);
}