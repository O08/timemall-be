package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO  extends PageDTO {
    private String q;
    @NotBlank(message = "employeeId required")
    private String employeeId;
    @NotBlank(message = "oasisId required")
    private String oasisId;
}
