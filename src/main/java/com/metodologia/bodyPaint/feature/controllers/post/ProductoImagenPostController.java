package com.metodologia.bodyPaint.feature.controllers.post;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metodologia.bodyPaint.config.BaseResponse;
import com.metodologia.bodyPaint.feature.dtos.request.ImportarImagenRequest;
import com.metodologia.bodyPaint.feature.services.interfaces.common.IProductoImagenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoImagenPostController {

    private final IProductoImagenService productoImagenService;

    @PostMapping(
        value = "/{id}/imagen",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public BaseResponse<Void> importarImagen(
            @PathVariable Long id,
            @ModelAttribute ImportarImagenRequest request
    ) {
        productoImagenService.importarImagen(id, request);
        return BaseResponse.ok(null, "Imagen importada correctamente");
    }
}
