package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.PhysicalOrderStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioChangePhysicalOrderStatusDTO {
    @NotBlank(message = "orderId required")
    private String orderId;
    @EnumCheck(enumClass = PhysicalOrderStatusEnum.class,message = "field: orderStatus, incorrect parameter value ")
    @NotBlank(message = "status required")
    private String orderStatus;
}
