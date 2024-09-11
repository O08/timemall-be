package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

@Data
public class ScienceSemiDataDTO {
    @NotBlank(message = "snippet is required")
    @Length(message = "snippet length must in range {min}-{max}",min = 1,max = 300)
    private String snippet;
    @NotBlank(message = "details is required")
    @Length(message = "details length must in range {min}-{max}",min = 1,max = 9000)
    private String details;

    @NotBlank(message = "fromWhere is required")
    @Length(message = "fromWhere length must in range {min}-{max}",min = 1,max = 90)
    private String fromWhere;

}
