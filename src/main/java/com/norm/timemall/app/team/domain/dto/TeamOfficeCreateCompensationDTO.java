package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamOfficeCreateCompensationDTO {
    @NotNull(message = "amount required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @Length(message = "description length must in range {min}-{max}",min = 0,max = 198)
    private String description;

    @NotNull(message = "direction required")
    @EnumCheck(enumClass = TransDirectionEnum.class,message = "field: direction, incorrect parameter value ,option: 1; -1;")
    private Integer direction;

    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 30)
    private String title;
}
