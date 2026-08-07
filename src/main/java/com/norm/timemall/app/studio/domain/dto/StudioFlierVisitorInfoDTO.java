package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioFlierVisitorInfoDTO {
    @NotBlank(message = "flierId required")
    private String flierId;
    private String visitorBrandId;
}
