package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.FlierInteractEventEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioInteractFlierDTO {
    @NotBlank(message = "flierId required")
    private String flierId;
    @NotBlank(message = "event required")
    @EnumCheck(enumClass = FlierInteractEventEnum.class,message = "field: event, incorrect parameter value")
    private String event;
}
