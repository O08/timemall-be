package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficePayOneEmployeeSalaryDTO {

    @NotBlank(message = "employeeId required")
    private String employeeId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 32)
    private String title;
}
