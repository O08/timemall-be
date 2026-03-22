package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.MembershipCardTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamMembershipBuyTierDTO {
    @NotBlank(message = "tierId is required")
    private String tierId;

    @EnumCheck(enumClass = MembershipCardTypeEnum.class,message = "field: cardType, incorrect parameter value ")
    @NotBlank(message = "cardType required")
    private String cardType;
}
