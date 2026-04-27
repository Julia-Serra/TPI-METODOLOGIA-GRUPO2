package com.metodologia.bodyPaint.feature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metodologia.bodyPaint.feature.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    boolean existsByEmail(String email);

}
