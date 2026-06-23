package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.DspMaterialTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamDspAddCaseMaterialDTO {

    private MultipartFile material;

    @NotBlank(message = "materialType required")
    @EnumCheck(enumClass = DspMaterialTypeEnum.class,message = "field: materialType, incorrect parameter value ,option: peacemaker informer defendant")
    private String materialType;

    @NotBlank(message = "caseNO required")
    private String caseNO;

}
