package com.metodologia.bodyPaint.feature.services.interfaces.common;

import com.metodologia.bodyPaint.feature.dtos.request.ImportarImagenRequest;

public interface IProductoImagenService {
    void importarImagen(Long productoId, ImportarImagenRequest request);
}
