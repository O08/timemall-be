package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
}
