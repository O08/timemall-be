package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamOasisNewTaskDTO {
    private String oasisId;
    private String title;
    private BigDecimal bonus;
}
