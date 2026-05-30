    package com.metodologia.bodyPaint.feature.services.impl.domain;

    import java.util.List;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import com.metodologia.bodyPaint.config.exceptions.BadRequestException;
    import com.metodologia.bodyPaint.feature.dtos.response.ReporteProductoMasVendidoResponse;
    import com.metodologia.bodyPaint.feature.repositories.PedidoRepository;
    import com.metodologia.bodyPaint.feature.services.interfaces.domain.IReporteProductosMasVendidosService;

    import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    public class ReporteProductosMasVendidosService implements IReporteProductosMasVendidosService {

        private final PedidoRepository pedidoRepository;

        @Override
        @Transactional(readOnly = true)
        public List<ReporteProductoMasVendidoResponse> generarReporte(Integer mes, Integer anio) {
        
        // NORMALIZACIÓN: Si el frontend manda un 0 o vacío que Spring tome como 0, lo pasamos a null real
        Integer mesFiltro = (mes != null && mes > 0) ? mes : null;
        Integer anioFiltro = (anio != null && anio > 0) ? anio : null;
        
        // Llamamos a la consulta pasando las variables ya normalizadas
        List<ReporteProductoMasVendidoResponse> resultado = pedidoRepository.obtenerProductosMasVendidos(mesFiltro, anioFiltro);
        
        // Criterio de aceptación: Probar generar reporte de productos sin datos de ventas (FALLA)
        if (resultado.isEmpty()) {
            throw new BadRequestException("No se encontraron registros de ventas para los filtros especificados.");
        }
        
        return resultado;
    }
    }