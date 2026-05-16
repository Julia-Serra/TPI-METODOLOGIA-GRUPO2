package com.metodologia.bodyPaint.feature.services.interfaces.domain;
import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.models.Direccion;

public interface IClienteCreateService {
    void crear(Cliente cliente);
    void agregarDireccion(String email, Direccion direccion);
}
