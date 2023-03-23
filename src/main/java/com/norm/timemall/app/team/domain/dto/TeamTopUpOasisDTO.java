package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamTopUpOasisDTO {
    private BigDecimal amount;
    private String oasisId;
}
