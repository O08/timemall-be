package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (office_employee_benefit)实体类
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("office_employee_benefit")
public class OfficeEmployeeBenefit extends Model<OfficeEmployeeBenefit> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * employeeId
     */
    private String employeeId;
    /**
     * pensionInsuranceBase
     */
    private BigDecimal pensionInsuranceBase;
    /**
     * pensionInsuranceCompanyRate
     */
    private BigDecimal pensionInsuranceCompanyRate;
    /**
     * pensionInsuranceEmployeeRate
     */
    private BigDecimal pensionInsuranceEmployeeRate;
    /**
     * medicalInsuranceBase
     */
    private BigDecimal medicalInsuranceBase;
    /**
     * medicalInsuranceCompanyRate
     */
    private BigDecimal medicalInsuranceCompanyRate;
    /**
     * medicalInsuranceEmployeeRate
     */
    private BigDecimal medicalInsuranceEmployeeRate;
    /**
     * unemploymentInsuranceBase
     */
    private BigDecimal unemploymentInsuranceBase;
    /**
     * unemploymentInsuranceCompanyRate
     */
    private BigDecimal unemploymentInsuranceCompanyRate;
    /**
     * unemploymentInsuranceEmployeeRate
     */
    private BigDecimal unemploymentInsuranceEmployeeRate;
    /**
     * occupationalInjuryInsuranceBase
     */
    private BigDecimal occupationalInjuryInsuranceBase;
    /**
     * occupationalInjuryInsuranceCompanyRate
     */
    private BigDecimal occupationalInjuryInsuranceCompanyRate;
    /**
     * occupationalInjuryInsuranceEmployeeRate
     */
    private BigDecimal occupationalInjuryInsuranceEmployeeRate;
    /**
     * birthInsuranceBase
     */
    private BigDecimal birthInsuranceBase;
    /**
     * birthInsuranceCompanyRate
     */
    private BigDecimal birthInsuranceCompanyRate;
    /**
     * birthInsuranceEmployeeRate
     */
    private BigDecimal birthInsuranceEmployeeRate;
    /**
     * housingProvidentFundsBase
     */
    private BigDecimal housingProvidentFundsBase;
    /**
     * housingProvidentFundsCompanyRate
     */
    private BigDecimal housingProvidentFundsCompanyRate;
    /**
     * housingProvidentFundsEmployeeRate
     */
    private BigDecimal housingProvidentFundsEmployeeRate;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}