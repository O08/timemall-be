package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.DspCaseStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamFetchDspCaseLibraryPageDTO extends PageDTO {

    private String q;

    @NotBlank(message = "sort required")
    private String sort;

    private String casePriority;

    @EnumCheck(enumClass = DspCaseStatusEnum.class,message = "field: caseStatus, incorrect parameter value ")
    private String caseStatus;

}
