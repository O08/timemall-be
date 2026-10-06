package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OfficeEmployeeBenefitRO {
    private BigDecimal birthInsuranceBase;
    private BigDecimal birthInsuranceCompanyRate;
    private BigDecimal birthInsuranceEmployeeRate;
    private BigDecimal housingProvidentFundsBase;
    private BigDecimal housingProvidentFundsCompanyRate;
    private BigDecimal housingProvidentFundsEmployeeRate;
    private BigDecimal medicalInsuranceBase;
    private BigDecimal medicalInsuranceCompanyRate;
    private BigDecimal medicalInsuranceEmployeeRate;
    private BigDecimal occupationalInjuryInsuranceBase;
    private BigDecimal occupationalInjuryInsuranceCompanyRate;
    private BigDecimal occupationalInjuryInsuranceEmployeeRate;
    private BigDecimal pensionInsuranceBase;
    private BigDecimal pensionInsuranceCompanyRate;
    private BigDecimal pensionInsuranceEmployeeRate;
    private BigDecimal unemploymentInsuranceBase;
    private BigDecimal unemploymentInsuranceCompanyRate;
    private BigDecimal unemploymentInsuranceEmployeeRate;
}
