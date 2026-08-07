package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.OfficeEmployeeStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOfficePayEmployeesSalaryDTO {

    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 32)
    private String title;

    @NotBlank(message = "employeeStatus required")
    @EnumCheck(enumClass = OfficeEmployeeStatusEnum.class,message = "field: employeeStatus, incorrect parameter value ")
    private String employeeStatus;
}
