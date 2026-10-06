package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudioVirtualProductShowcaseAddDTO {

    private MultipartFile showcase;

    @NotBlank(message = "productId required")
    private String productId;

}
