package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.VirtualOrderTagEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioFetchVirtualOrderMaintenanceDTO {

    @NotBlank(message = "orderId required")
    private String orderId;

    @NotBlank(message = "tag required")
    @EnumCheck(enumClass = VirtualOrderTagEnum.class,message = "field: tag, incorrect parameter value")
    private String tag;

}
