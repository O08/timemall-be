package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.OfficeEmployeeGenreEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class TeamOfficeTypeInEmployeeDTO {

    @NotBlank(message = "uid required")
    private String uid;

    @NotBlank(message = "departmentId required")
    private String departmentId;

    @NotBlank(message = "role required")
    @Length(message = "role length must in range {min}-{max}",min = 0,max = 30)
    private String role;

    @NotBlank(message = "employeeNumber required")
    @Length(message = "employeeNumber length must in range {min}-{max}",min = 0,max = 30)
    @Pattern(regexp="^\\w+$", message="员工编号字符不符合要求")
    private String employeeNumber;

    @NotBlank(message = "employeeName required")
    @Length(message = "employeeName length must in range {min}-{max}",min = 0,max = 16)
    private String employeeName;

    @NotBlank(message = "genre required")
    @EnumCheck(enumClass = OfficeEmployeeGenreEnum.class,message = "field: genre, incorrect parameter value ")
    private String genre;


    @NotBlank(message = "officeLocation required")
    @Length(message = "officeLocation length must in range {min}-{max}",min = 0,max = 80)
    private String officeLocation;

    @NotNull(message = "salary required")
    @Positive(message = "salary must be positive")
    private BigDecimal salary;


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
    @PositiveOrZero(message = "pensionInsuranceBase must be positive or zero")
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
