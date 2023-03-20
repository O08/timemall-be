package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TeamOasisNewTaskDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;
    @NotBlank(message = "title required")
    private String title;
    @Positive(message = "bonus required and positive")
    @NotNull(message = "bonus is required and must be number")
    private BigDecimal bonus;
}
