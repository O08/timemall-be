package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioChangePhysicalProductStatusDTO {
    @NotBlank(message = "productId required")
    private String productId;

    @EnumCheck(enumClass = ProductStatusEnum.class,message = "field: status, incorrect parameter value ")
    @NotBlank(message = "status required")
    private String status;
}
