package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.enums.MillstoneAcEnum;
import com.norm.timemall.app.base.enums.SbuEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PodMillstonePermissionDTO {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "ac is required")
    @EnumCheck(enumClass = MillstoneAcEnum.class,message = "field: ac, incorrect parameter value ,option: 1 0")
    private String ac;

}
