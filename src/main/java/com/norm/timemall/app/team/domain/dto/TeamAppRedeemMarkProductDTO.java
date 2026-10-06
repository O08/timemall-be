package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppRedeemProductStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppRedeemMarkProductDTO {
    @NotBlank(message = "productId required")
    private String productId;

    @EnumCheck(enumClass = AppRedeemProductStatusEnum.class,message = "field: status, incorrect parameter value ")
    @NotBlank(message = "status required")
    private String status;
}
