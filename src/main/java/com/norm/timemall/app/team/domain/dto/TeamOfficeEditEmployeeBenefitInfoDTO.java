package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class TeamOfficeEditEmployeeBenefitInfoDTO {
    @NotBlank(message = "employeeId required")
    private String employeeId;


    @NotNull(message = "housingProvidentFundsBase required")
    @PositiveOrZero(message = "housingProvidentFundsBase must be positive or zero")
    private BigDecimal housingProvidentFundsBase;

    @Range(min = 0,max = 99,message = "housingProvidentFundsCompanyRate range in {min} - {max}")
    @NotNull(message = "housingProvidentFundsCompanyRate required")
    private BigDecimal housingProvidentFundsCompanyRate;

    @Range(min = 0,max = 99,message = "housingProvidentFundsCompanyRate range in {min} - {max}")
    @NotNull(message = "housingProvidentFundsEmployeeRate required")
    private BigDecimal housingProvidentFundsEmployeeRate;

    @NotNull(message = "medicalInsuranceBase required")
    @PositiveOrZero(message = "medicalInsuranceBase must be positive or zero")
    private BigDecimal medicalInsuranceBase;

    @Range(min = 0,max = 99,message = "medicalInsuranceCompanyRate range in {min} - {max}")
    @NotNull(message = "medicalInsuranceCompanyRate required")
    private BigDecimal medicalInsuranceCompanyRate;

    @Range(min = 0,max = 99,message = "medicalInsuranceEmployeeRate range in {min} - {max}")
    @NotNull(message = "medicalInsuranceEmployeeRate required")
    private BigDecimal medicalInsuranceEmployeeRate;

    @NotNull(message = "occupationalInjuryInsuranceBase required")
    @PositiveOrZero(message = "occupationalInjuryInsuranceBase must be positive or zero")
    private BigDecimal occupationalInjuryInsuranceBase;

    @Range(min = 0,max = 99,message = "occupationalInjuryInsuranceCompanyRate range in {min} - {max}")
    @NotNull(message = "occupationalInjuryInsuranceCompanyRate required")
    private BigDecimal occupationalInjuryInsuranceCompanyRate;

    @Range(min = 0,max = 99,message = "occupationalInjuryInsuranceEmployeeRate range in {min} - {max}")
    @NotNull(message = "occupationalInjuryInsuranceEmployeeRate required")
    private BigDecimal occupationalInjuryInsuranceEmployeeRate;

    @NotNull(message = "pensionInsuranceBase required")
    @PositiveOrZero(message = "pensionInsuranceBase must be positive o zero")
    private BigDecimal pensionInsuranceBase;

    @Range(min = 0,max = 99,message = "pensionInsuranceCompanyRate range in {min} - {max}")
    @NotNull(message = "pensionInsuranceCompanyRate required")
    private BigDecimal pensionInsuranceCompanyRate;

    @Range(min = 0,max = 99,message = "pensionInsuranceEmployeeRate range in {min} - {max}")
    @NotNull(message = "pensionInsuranceEmployeeRate required")
    private BigDecimal pensionInsuranceEmployeeRate;

    @NotNull(message = "birthInsuranceBase required")
    @PositiveOrZero(message = "birthInsuranceBase must be positive or zero")
    private BigDecimal birthInsuranceBase;

    @Range(min = 0,max = 99,message = "birthInsuranceCompanyRate range in {min} - {max}")
    @NotNull(message = "birthInsuranceCompanyRate required")
    private BigDecimal birthInsuranceCompanyRate;

    @Range(min = 0,max = 99,message = "birthInsuranceEmployeeRate range in {min} - {max}")
    @NotNull(message = "birthInsuranceEmployeeRate required")
    private BigDecimal birthInsuranceEmployeeRate;

    @NotNull(message = "unemploymentInsuranceBase required")
    @PositiveOrZero(message = "unemploymentInsuranceBase must be positive or zero")
    private BigDecimal unemploymentInsuranceBase;

    @Range(min = 0,max = 99,message = "unemploymentInsuranceCompanyRate range in {min} - {max}")
    @NotNull(message = "unemploymentInsuranceCompanyRate required")
    private BigDecimal unemploymentInsuranceCompanyRate;

    @Range(min = 0,max = 99,message = "unemploymentInsuranceEmployeeRate range in {min} - {max}")
    @NotNull(message = "unemploymentInsuranceEmployeeRate required")
    private BigDecimal unemploymentInsuranceEmployeeRate;
}
