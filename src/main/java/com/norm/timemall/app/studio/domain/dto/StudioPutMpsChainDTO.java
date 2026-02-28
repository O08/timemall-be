package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

@Data
public class StudioPutMpsChainDTO {
    @NotBlank(message = "title required")
    @Length(message = "title range in {min}-{max}",min = 1,max = 80)
    private String title;
    @NotBlank(message = "tag required")
    private String tag;
    @NotBlank(message = "id required")
    private String id;

}
