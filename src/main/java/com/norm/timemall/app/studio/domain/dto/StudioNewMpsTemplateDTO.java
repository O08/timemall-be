package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class StudioNewMpsTemplateDTO {
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
}
