package com.norm.timemall.app.pod.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PodRefundDTO {
    @NotBlank(message = "scene required")
    private String scene;
    @NotBlank(message = "param required")
    private String param;
}
