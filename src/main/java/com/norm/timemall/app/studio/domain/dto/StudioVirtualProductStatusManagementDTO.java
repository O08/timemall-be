package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioVirtualProductStatusManagementDTO {

    @NotBlank(message = "productId required")
    private String productId;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = ProductStatusEnum.class,message = "field: status, incorrect parameter value ,option: draft-1; online-2; offline-3")
    private String status;

}
