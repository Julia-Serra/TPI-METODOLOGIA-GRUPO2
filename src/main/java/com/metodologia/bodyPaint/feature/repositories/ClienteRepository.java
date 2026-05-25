package com.metodologia.bodyPaint.feature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.metodologia.bodyPaint.feature.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    boolean existsByEmail(String email);
    Optional<Cliente> findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.email) = LOWER(:email)")
    Optional<Cliente> findByEmailIgnoreCase(@Param("email") String email);

}
