package com.norm.timemall.app.pay.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithDrawDTO {
    private String toAccountId;
    private BigDecimal amount;
}
