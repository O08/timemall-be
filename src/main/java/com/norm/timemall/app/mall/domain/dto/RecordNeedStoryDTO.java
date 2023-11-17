package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RecordNeedStoryDTO {
    @NotBlank(message = "descriptions is required")
    @Length(message = "descriptions length must in range {min}-{max}",min = 1,max = 300)
    private String descriptions;

    @Positive(message = "budget value should be positive.")
    private Integer budget;

    @Length(message = "contactInfo length must in range {min}-{max}",min = 1,max = 300)
    private String contactInfo;
}
