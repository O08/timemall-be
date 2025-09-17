package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WithdrawToAlipayCreditSettingDTO {
    @NotBlank(message = "caseNO required")
    private String caseNO;

    @NotBlank(message = "offOrOn required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: offOrOn, incorrect parameter value ,option: 1 0")
    private String offOrOn;
}
