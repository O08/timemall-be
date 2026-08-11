package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficeCreateEmployeeKvPairDTO {

    @NotBlank(message = "employeeId required")
    private String employeeId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 30)
    private String title;

    @NotBlank(message = "content required")
    @Length(message = "content length must in range {min}-{max}",min = 0,max = 72)
    private String content;
}
