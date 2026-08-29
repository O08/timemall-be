package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsPlanTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
@Data
public class StudioChangeSubsPlanDTO {
    @Length(message = "description range in {min}-{max}",min = 1,max = 172)
    @NotBlank(message = "description required")
    private String description;

    @Length(message = "features range in {min}-{max}",min = 1,max = 4000)
    @NotBlank(message = "features required")
    private String features;


    @Length(message = "planName range in {min}-{max}",min = 1,max = 32)
    @NotBlank(message = "planName required")
    private String planName;


    @NotNull(message = "price is required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;

    @NotBlank(message = "planId required")
    private String planId;

    @Range(min = 1L,max = 25,message = "trialPeriod range in {min} - {max}")
    @Positive(message = "trialPeriod  must be positive")
    private Integer trialPeriod;

    @Range(min = 1L,max = 25,message = "gracePeriod range in {min} - {max}")
    @Positive(message = "gracePeriod  must be positive")
    private Integer gracePeriod;
}
