package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OfficeEmployeeBenefit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.OfficeEmployeeBenefitRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (office_employee_benefit)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeEmployeeBenefitMapper extends BaseMapper<OfficeEmployeeBenefit> {
@Select("select b.pension_insurance_base,b.pension_insurance_company_rate,b.pension_insurance_employee_rate,b.medical_insurance_base,b.medical_insurance_company_rate,b.medical_insurance_employee_rate,b.unemployment_insurance_base,b.unemployment_insurance_company_rate,b.unemployment_insurance_employee_rate,b.occupational_injury_insurance_base,b.occupational_injury_insurance_company_rate,b.occupational_injury_insurance_employee_rate,b.birth_insurance_base,b.birth_insurance_company_rate,b.birth_insurance_employee_rate,b.housing_provident_funds_base,b.housing_provident_funds_company_rate,b.housing_provident_funds_employee_rate from office_employee_benefit b where b.employee_id=#{employeeId}")
    OfficeEmployeeBenefitRO selectOneBenefitByEmployeeId(@Param("employeeId") String employeeId);

}
