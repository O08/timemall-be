package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamOasisRoleConfigChannelDTO {

    @NotBlank(message = "roleId required")
    private String roleId;

    @NotBlank(message = "oasisChannelId required")
    private String oasisChannelId;

    @NotBlank(message = "assigned required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: assigned, incorrect parameter value ,option: on-1; off-0;")
    private String assigned;
}
