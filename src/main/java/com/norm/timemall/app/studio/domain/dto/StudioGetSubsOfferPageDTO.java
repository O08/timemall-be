package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.SubsOfferStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;

@Data
public class StudioGetSubsOfferPageDTO extends PageDTO {

    private String q;
    @EnumCheck(enumClass = SubsOfferStatusEnum.class,message = "field: tag, incorrect parameter value ")
    private String tag;
    private String type;

}
