package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficeRenameEmployeeMaterialDTO {
    @NotBlank(message = "id required")
    private String id;
    @NotBlank(message = "materialName required")
    @Length(message = "materialName length must in range {min}-{max}",min = 1,max = 32)
    private String materialName;
}
