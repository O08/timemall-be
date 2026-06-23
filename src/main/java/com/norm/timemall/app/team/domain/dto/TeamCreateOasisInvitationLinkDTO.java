package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.OasisInvitationLinkExpireTimeTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class TeamCreateOasisInvitationLinkDTO {
    @EnumCheck(enumClass = OasisInvitationLinkExpireTimeTypeEnum.class,message = "field: expireTime, incorrect parameter value ")
    private String expireTime;

    @NotBlank(message = "grantedOasisRoleId required")
    private String grantedOasisRoleId;
    // 0 usually represents unlimited if handled by logic
    @Range(min = 0L,max = 10000,message = "maxUses range in {min} - {max}")
    @PositiveOrZero(message = "maxUses required and must be positive or zero")
    @NotNull(message = "maxUses is required")
    private Integer maxUses;

    @NotBlank(message = "oasisId required")
    private String oasisId;
}
