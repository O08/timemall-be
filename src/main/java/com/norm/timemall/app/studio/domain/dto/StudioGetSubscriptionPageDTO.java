package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.SubscriptionStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;

@Data
public class StudioGetSubscriptionPageDTO extends PageDTO {
    private String q;
    private String filter;
    @EnumCheck(enumClass = SubscriptionStatusEnum.class,message = "field: tag, incorrect parameter value ")
    private String tag;
}
