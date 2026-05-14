package com.metodologia.bodyPaint.feature.dtos.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ImportarImagenRequest {

    private MultipartFile archivo;

}
