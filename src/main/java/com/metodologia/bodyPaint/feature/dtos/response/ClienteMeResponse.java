package com.metodologia.bodyPaint.feature.dtos.response;

import com.metodologia.bodyPaint.feature.models.Rol;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClienteMeResponse {

    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;

}
