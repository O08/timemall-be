package com.norm.timemall.app.team.domain.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamEditOasisRoleDTO {

    @NotBlank(message = "roleId required")
    private String roleId;

    @NotBlank(message = "roleName required")
    @Length(message = "roleName length must in range {min}-{max}",min = 1,max = 32)
    private String roleName;

    @NotBlank(message = "roleDesc required")
    @Length(message = "roleDesc length must in range {min}-{max}",min = 1,max = 200)
    private String roleDesc;
}
