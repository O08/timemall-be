package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.SubsPlanStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;

@Data
public class StudioGetSubsPlanPageDTO extends PageDTO {
    private String q;
    @EnumCheck(enumClass = SubsPlanStatusEnum.class,message = "field: tag, incorrect parameter value ，optional: 1 2 3 ")
    private String tag;
}
