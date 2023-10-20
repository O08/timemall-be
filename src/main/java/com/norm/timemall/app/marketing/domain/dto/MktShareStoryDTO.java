package com.norm.timemall.app.marketing.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class MktShareStoryDTO {
    @NotBlank(message = "story required")
    @Length(message = "story range in {min}-{max}",min = 1,max = 300)
    private String story;

    @NotBlank(message = "puzzleVersion required")
    private String puzzleVersion;
}
