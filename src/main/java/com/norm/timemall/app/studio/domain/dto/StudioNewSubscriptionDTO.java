package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsBillCalendarEnum;
import com.norm.timemall.app.base.enums.SubsPlanTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioNewSubscriptionDTO {

    @NotBlank(message = "planId required")
    private String planId;

    @NotBlank(message = "billCalendar required")
    @EnumCheck(enumClass = SubsBillCalendarEnum.class,message = "field: billCalendar, incorrect parameter value ,option: monthly quarterly yearly")
    private String billCalendar;

    private String promoCode;
}
