package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.OfficeEmployeeStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamOfficeGivePerkDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 32)
    private String title;

    @EnumCheck(enumClass = OfficeEmployeeStatusEnum.class,message = "field: employeeStatus, incorrect parameter value ")
    @NotBlank(message = "employeeStatus required")
    private String employeeStatus;

    @NotNull(message = "grossAmount required")
    @Positive(message = "grossAmount must be positive")
    private BigDecimal grossAmount;

    @NotNull(message = "deduction required")
    @PositiveOrZero(message = "deduction must be positive or zero")
    private BigDecimal deduction;

    @NotNull(message = "withholdAndRemitTax required")
    @PositiveOrZero(message = "withholdAndRemitTax must be positive or zero")
    private BigDecimal withholdAndRemitTax;
}
