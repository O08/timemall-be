package com.norm.timemall.app.ms.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

@Data
public class MsCreateMpsRoomDTO {
    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 80)
    private String title;
    @NotBlank(message = "mpsId required")
    private String mpsId;
}
