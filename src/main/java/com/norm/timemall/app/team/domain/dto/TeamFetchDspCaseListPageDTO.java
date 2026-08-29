package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.DspCaseStatusEnum;
import com.norm.timemall.app.base.enums.DspMaterialTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamFetchDspCaseListPageDTO extends PageDTO {
    private String q;

    @NotBlank(message = "materialType required")
    @EnumCheck(enumClass = DspMaterialTypeEnum.class,message = "field: materialType, incorrect parameter value ,option: informer defendant")
    private String materialType;


    @EnumCheck(enumClass = DspCaseStatusEnum.class,message = "field: caseStatus, incorrect parameter value ")
    private String caseStatus;

}
