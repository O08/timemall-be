package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioEditFlierDTO {

    @NotBlank(message = "flierId required")
    private String flierId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 32)
    private String title;


    @NotBlank(message = "description required")
    @Length(message = "description length must in range {min}-{max}",min = 1,max = 72)
    private String description;

    @Length(message = "ctaLink length must in range {min}-{max}",min = 0,max = 300)
    private String ctaLink;
}
