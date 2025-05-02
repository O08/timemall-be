package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioRefundDTO {

    @NotBlank(message = "scene required")
    private String scene;
    @NotBlank(message = "param required")
    private String param;


}
