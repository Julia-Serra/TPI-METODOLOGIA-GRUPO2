package com.metodologia.bodyPaint.feature.repositories;

import com.metodologia.bodyPaint.feature.models.CuponDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuponDescuentoRepository extends JpaRepository<CuponDescuento, Long> {

    boolean existsByCodigo(String codigo);

    Optional<CuponDescuento> findByCodigo(String codigo);
    @Query("""
select c
from CuponDescuento c
join c.clientes cl
where lower(cl.email)=lower(:email)
and c.usado = false
""")
    List<CuponDescuento> findCuponesValidosPorEmail(
            @Param("email") String email
    );
}