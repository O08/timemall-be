package com.norm.timemall.app.studio.domain.dto;


import com.norm.timemall.app.base.enums.ProposalMaterialTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudioProposalMaterialUploadDTO {
    private MultipartFile material;

    @NotBlank(message = "materialType required")
    @EnumCheck(enumClass = ProposalMaterialTypeEnum.class,message = "field: materialType, incorrect parameter value ,option: seller,buyer,deliver")
    private String materialType;

    @NotBlank(message = "proposalId required")
    private String proposalId;
}
