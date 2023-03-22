package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StudioTopUpDTO {
    @NotNull(message = "amountLittle is required")
    private BigDecimal amountLittle;
    @NotNull(message = "amountBig is required")
    private BigDecimal amountBig;
}
