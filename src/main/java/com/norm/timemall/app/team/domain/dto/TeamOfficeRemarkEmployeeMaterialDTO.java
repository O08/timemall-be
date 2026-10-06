package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficeRemarkEmployeeMaterialDTO {
    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "remark required")
    @Length(message = "remark length must in range {min}-{max}",min = 0,max = 172)
    private String remark;
}
