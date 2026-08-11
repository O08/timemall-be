package com.norm.timemall.app.pod.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PodCoopApplyForProgramDTO {
    @NotBlank(message = "programId required")
    private String programId;

    @NotBlank(message = "message required")
    @Length(message = "message length must in range {min}-{max}",min = 1,max = 400)
    private String message;
}
