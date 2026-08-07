package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudioChangeFlierMaterialDTO {
    @NotBlank(message = "flierId required")
    private String flierId;

    private MultipartFile material;
}
