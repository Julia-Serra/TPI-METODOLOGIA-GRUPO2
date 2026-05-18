package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;

public interface IDarDeBajaProductoService {
    ProductoResponse ejecutar(Long id);
}