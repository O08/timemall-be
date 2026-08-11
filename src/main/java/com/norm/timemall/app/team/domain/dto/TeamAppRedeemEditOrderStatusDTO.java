package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppRedeemOrderStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppRedeemEditOrderStatusDTO {
    @NotBlank(message = "orderId required")
    private String orderId;

    @EnumCheck(enumClass = AppRedeemOrderStatusEnum.class,message = "field: status, incorrect parameter value")
    @NotBlank(message = "status required")
    private String status;
}
