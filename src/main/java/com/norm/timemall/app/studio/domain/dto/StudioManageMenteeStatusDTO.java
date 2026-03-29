package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.MenteeStatusEnum;
import com.norm.timemall.app.base.enums.SubsPlanTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioManageMenteeStatusDTO {

    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = MenteeStatusEnum.class,message = "field: status, incorrect parameter value ,option: 1-application,2 - training,3 - graduated")
    private String status;
}
