package com.metodologia.bodyPaint.feature.dtos.request;

import lombok.Data;

@Data
public class AgregarProductoCarritoRequest {

    private Long productoId;
    private int cantidad;
}
