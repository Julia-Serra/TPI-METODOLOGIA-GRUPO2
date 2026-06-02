package com.metodologia.bodyPaint.feature.services.interfaces.domain;

import com.metodologia.bodyPaint.feature.dtos.request.AplicarCuponRequest;
import com.metodologia.bodyPaint.feature.dtos.response.AplicarCuponResponse;

public interface IAplicarCuponService {

    AplicarCuponResponse aplicar(AplicarCuponRequest request);
}
