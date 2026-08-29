package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
@Data
public class StudioCreateFlierDTO {

    private MultipartFile material;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 32)
    private String title;


    @NotBlank(message = "description required")
    @Length(message = "description length must in range {min}-{max}",min = 1,max = 72)
    private String description;

    @Length(message = "ctaLink length must in range {min}-{max}",min = 0,max = 300)
    private String ctaLink;
}
