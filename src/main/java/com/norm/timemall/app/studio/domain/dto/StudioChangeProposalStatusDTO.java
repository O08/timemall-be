package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.ProposalProjectStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioChangeProposalStatusDTO {
    @NotBlank(message = "id required")
    private String id;
    @EnumCheck(enumClass = ProposalProjectStatusEnum.class,message = "field: projectStatus, incorrect parameter value ,option: draft-0; SIGNED-1; DELIVERING-2; COMPLETED-3; SUSPENDED-4")
    @NotBlank(message = "projectStatus required")
    private String projectStatus;
}
