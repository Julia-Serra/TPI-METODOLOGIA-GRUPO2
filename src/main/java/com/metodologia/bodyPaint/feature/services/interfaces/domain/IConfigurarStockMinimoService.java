package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.request.ConfigurarStockMinimoRequest;
import com.metodologia.bodyPaint.feature.dtos.response.ProductoResponse;

public interface IConfigurarStockMinimoService {
    ProductoResponse ejecutar(Long id, ConfigurarStockMinimoRequest request);
}