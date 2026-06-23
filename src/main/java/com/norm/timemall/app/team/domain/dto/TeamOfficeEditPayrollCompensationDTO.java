package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficeEditPayrollCompensationDTO {
    @NotBlank(message = "payrollId required")
    private String payrollId;

    @NotBlank(message = "compensationJson required")
    @Length(message = "compensationJson length must in range {min}-{max}",min = 1,max = 5000)
    private String compensationJson;
}
