package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
@Data
public class TeamOasisChangeTaskDTO {

    @NotBlank(message = "id required")
    private String id;
    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 80)
    private String title;
    @Positive(message = "bonus required and positive")
    @NotNull(message = "bonus is required and must be number")
    private BigDecimal bonus;
    @NotBlank(message = "sow required")
    @Length(message = "sow length must in range {min}-{max}",min = 1,max = 4500)
    private String sow;

}
