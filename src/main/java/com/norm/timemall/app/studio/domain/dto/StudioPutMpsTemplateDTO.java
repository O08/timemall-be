package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.DifficultyLevelEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class StudioPutMpsTemplateDTO {
    @NotBlank(message = "title required")
    @Length(message = "title range in {min}-{max}",min = 1,max = 80)
    private String title;
    @NotBlank(message = "sow required")
    private String sow;
    @NotBlank(message = "piece required")
    @Length(message = "piece range in {min}-{max}",min = 1,max = 80)
    private String piece;
    @NotNull(message = "bonus is required")
    @Positive(message = "bonus required and must be positive")
    private BigDecimal bonus;
    private String firstSupplier;
    @Positive(message = "duration must be positive")
    private Integer duration;
    @NotBlank(message = "chainId required")
    private String chainId;
    @Positive(message = "deliveryCycle must be positive")
    private Integer deliveryCycle;
    @Positive(message = "contractValidityPeriod must be positive")
    private Integer contractValidityPeriod;
    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "skills required")
    @Length(message = "skills range in {min}-{max}",min = 1,max = 400)
    private String skills;

    @NotBlank(message = "difficulty required")
    @EnumCheck(enumClass = DifficultyLevelEnum.class,message = "field: difficulty, incorrect parameter value")
    private String difficulty;

    @Positive(message = "experience must be positive")
    @Range(min = 1L,max = 40,message = "experience range in {min} - {max}")
    private Integer experience;

    @NotBlank(message = "location required")
    @Length(message = "location range in {min}-{max}",min = 1,max = 8)
    private String location;

    @NotNull(message = "bidElectricity is required")
    @Positive(message = "bidElectricity must be positive")
    private Integer bidElectricity;
}
