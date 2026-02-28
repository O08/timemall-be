package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class StudioPutCellPlanDTO {
    @NotBlank(message = "cellId required")
    private String cellId;
    private String planId;
    @NotBlank(message = "planType required")
    private String planType;
    @NotBlank(message = "title required")
    @Length(message = "title range in {min}-{max}",min = 1,max = 80)
    private String title;
    @NotBlank(message = "content required")
    @Length(message = "content range in {min}-{max}",min = 1,max = 200)
    private String content;
    @NotBlank(message = "feature required")
    @Length(message = "feature range in {min}-{max}",min = 1,max = 600)
    private String feature;
    @NotNull(message = "price is required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;

}
