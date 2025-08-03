package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.SubsBillStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;

@Data
public class StudioGetSubsBillingPageDTO extends PageDTO {
    private String q;
    @EnumCheck(enumClass = SubsBillStatusEnum.class,message = "field: tag, incorrect parameter value")
    private String tag;
}
