package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsPlanStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioChangeSubsPlanStatusDTO {

    @NotBlank(message = "planId required")
    private String planId;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = SubsPlanStatusEnum.class,message = "field: status, incorrect parameter value ,option: 2 3")
    private String status;
}
