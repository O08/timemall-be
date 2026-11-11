package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioProposalMaterialRenameDTO {

    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "materialName required")
    @Length(message = "materialName length must in range {min}-{max}",min = 1,max = 100)
    private String materialName;

}
