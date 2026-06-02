package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.request.GenerarCuponRequest;
import com.metodologia.bodyPaint.feature.dtos.response.GenerarCuponResponse;

public interface IGenerarCuponService {

    GenerarCuponResponse generar(GenerarCuponRequest request);
}