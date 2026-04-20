package com.metodologia.bodyPaint.feature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metodologia.bodyPaint.feature.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
