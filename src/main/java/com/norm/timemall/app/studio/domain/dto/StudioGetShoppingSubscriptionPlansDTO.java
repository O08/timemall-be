package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsPlanTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioGetShoppingSubscriptionPlansDTO{
    @NotBlank(message = "sellerHandle required")
    private String sellerHandle;
    @NotBlank(message = "productCode required")
    private String productCode;

    private String planId;

    @EnumCheck(enumClass = SubsPlanTypeEnum.class,message = "field: planType, incorrect parameter value ,option: standard flex")
    private String planType;

    private String mode;

}
