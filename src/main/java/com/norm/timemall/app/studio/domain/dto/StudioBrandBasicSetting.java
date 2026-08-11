package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioBrandBasicSetting {
    private String occupationCode;
    private String selfDefinedOccupation;
    private String industryCode;
    private String brandTypeCode;
    @NotBlank(message = "supportFreeCooperation required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: supportFreeCooperation, incorrect parameter value ,option: on-1; off-0;")
    private String supportFreeCooperation;
    private String cooperationScope;

    @NotBlank(message = "availableForWork required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: availableForWork, incorrect parameter value ,option: on-1; off-0;")
    private String availableForWork;

    @NotBlank(message = "hiring required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: hiring, incorrect parameter value ,option: on-1; off-0;")
    private String hiring;

    private String typeOfBusiness;
    private String industry;
    @NotBlank(message = "freeNightCounsellor required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: freeNightCounsellor, incorrect parameter value ,option: on-1; off-0;")
    private String freeNightCounsellor;
    private String businessScope;
    @NotBlank(message = "enableMentorship required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: enableMentorship, incorrect parameter value ,option: on-1; off-0;")
    private String enableMentorship;
    @NotBlank(message = "enableFoodForWork required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: enableFoodForWork, incorrect parameter value ,option: on-1; off-0;")
    private String enableFoodForWork;
}
