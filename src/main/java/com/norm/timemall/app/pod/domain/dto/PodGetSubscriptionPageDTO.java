package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.SubscriptionStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;

@Data
public class PodGetSubscriptionPageDTO extends PageDTO {
    private String q;

    @EnumCheck(enumClass = SubscriptionStatusEnum.class,message = "field: tag, incorrect parameter value ")
    private String tag;
}
