package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.ProposalMaterialTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioGetProposalMaterialsDTO {
    @NotBlank(message = "proposalId required")
    private String proposalId;

    @NotBlank(message = "materialType required")
    @EnumCheck(enumClass = ProposalMaterialTypeEnum.class,message = "field: materialType, incorrect parameter value ,option: seller,buyer,deliver")
    private String materialType;

}
