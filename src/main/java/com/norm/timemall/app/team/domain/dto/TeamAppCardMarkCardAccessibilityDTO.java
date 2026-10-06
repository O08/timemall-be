package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppCardMarkCardAccessibilityDTO {
    @NotBlank(message = "available required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: available, 0 - error； 1 - normal")
    private String available;

    @NotBlank(message = "id required")
    private String id;
}
