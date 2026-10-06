package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsOfferStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioSubsOfferChangeStatusDTO {
    @NotBlank(message = "offerId required")
    private String offerId;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = SubsOfferStatusEnum.class,message = "field: status, incorrect parameter value ,option: 2 3")
    private String status;

}
