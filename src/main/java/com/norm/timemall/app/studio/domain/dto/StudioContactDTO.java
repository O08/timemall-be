package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioContactDTO {
    private String phone;
    private String email;
    @Length(message = "realName range in {min}-{max}",min = 0,max = 36)
    private String realName;
}
