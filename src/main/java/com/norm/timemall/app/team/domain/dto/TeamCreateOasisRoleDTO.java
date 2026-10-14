package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamCreateOasisRoleDTO {

    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotBlank(message = "roleCode required")
    @Length(message = "roleCode length must in range {min}-{max}",min = 1,max = 18)
    @Pattern(regexp="^\\w+$", message="身份组编码字符不符合要求")
    private String roleCode;



    @NotBlank(message = "roleName required")
    @Length(message = "roleName length must in range {min}-{max}",min = 1,max = 32)
    private String roleName;

    @NotBlank(message = "roleDesc required")
    @Length(message = "roleDesc length must in range {min}-{max}",min = 1,max = 200)
    private String roleDesc;

}
