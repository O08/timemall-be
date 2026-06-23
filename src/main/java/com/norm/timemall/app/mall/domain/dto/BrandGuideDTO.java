package com.norm.timemall.app.mall.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandGuideDTO {
    /**
     * access way: 1 raw; 2 brand ; 3 handle;
     */
    @NotBlank(message = "accessWay is required,option: 1 2 3")
    private String accessWay;
    @NotBlank(message = "originalUrl is required")
    private String originalUrl;
    /**
     * access param
     */
    @NotBlank(message = "param is required")
    private String param;
}
