package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamOfficeChangeCompensationDTO {
    @NotNull(message = "amount required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @Length(message = "description length must in range {min}-{max}",min = 0,max = 198)
    private String description;

    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: status, incorrect parameter value ,option: 1; 0;")
    private String status;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 30)
    private String title;
}
