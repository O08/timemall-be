package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class StudioPutMpsPaperDTO {
    @NotBlank(message = "sow required")
    private String sow;
    @NotNull(message = "bonus is required")
    @Positive(message = "bonus required and must be positive")
    private BigDecimal bonus;
    @NotBlank(message = "paperId required")
    private String paperId;
}
