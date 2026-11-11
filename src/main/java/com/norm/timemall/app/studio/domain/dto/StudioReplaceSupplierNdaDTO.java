package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudioReplaceSupplierNdaDTO {

    private MultipartFile material;
    @NotBlank(message = "id不能为空")
    private String id;
}
