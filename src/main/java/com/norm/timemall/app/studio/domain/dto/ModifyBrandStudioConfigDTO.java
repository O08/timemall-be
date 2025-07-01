package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ModifyBrandStudioConfigDTO {

    @NotBlank(message = "hiLinkName required")
    @Length(message = "hiLinkName range in {min}-{max}",min = 1,max = 6)
    private String hiLinkName;

    @NotBlank(message = "hiLinkUrl required")
    @Length(message = "hiLinkUrl range in {min}-{max}",min = 1,max = 480)
    private String hiLinkUrl;

}
