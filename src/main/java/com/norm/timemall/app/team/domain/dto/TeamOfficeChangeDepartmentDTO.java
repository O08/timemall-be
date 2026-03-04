package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficeChangeDepartmentDTO {

    @NotBlank(message = "departmentId required")
    private String departmentId;

    @Length(message = "description length must in range {min}-{max}",min = 0,max = 198)
    private String description;

    private String leaderEmployeeId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 30)
    private String title;
}
