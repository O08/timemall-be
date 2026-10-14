package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamMembershipMarkTierDTO {
    @NotBlank(message = "tierId required")
    private String tierId;

    @EnumCheck(enumClass = ProductStatusEnum.class,message = "field: status, incorrect parameter value ")
    @NotBlank(message = "status required")
    private String status;
}
