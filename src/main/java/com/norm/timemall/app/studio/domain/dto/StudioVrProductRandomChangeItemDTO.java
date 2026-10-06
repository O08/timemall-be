package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioVrProductRandomChangeItemDTO {
    @NotBlank(message = "pack required")
    @Length(message = "pack range in {min}-{max}",min = 1,max = 300)
    private String pack;

    @NotBlank(message = "merchandiseId required")
    private String merchandiseId;
}
