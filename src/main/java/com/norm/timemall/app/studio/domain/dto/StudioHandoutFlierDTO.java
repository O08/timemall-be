package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioHandoutFlierDTO {
    @NotBlank(message = "flierId required")
    private String flierId;
    @NotBlank(message = "receiverBrandId required")
    private String receiverBrandId;
}
