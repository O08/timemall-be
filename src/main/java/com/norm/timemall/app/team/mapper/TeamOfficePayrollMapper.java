package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficePayroll;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficePayEmployeesSalaryDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryAdminPayrollPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryEmployeePayrollPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchPayrollInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryAdminPayrollPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryEmployeePayrollPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;

/**
 * (office_payroll)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficePayrollMapper extends BaseMapper<OfficePayroll> {

    int insertBatchSomeColumn(Collection<OfficePayroll> payrollList);

    IPage<TeamOfficeQueryAdminPayrollPageRO> selectPageByQ(IPage<TeamOfficeQueryAdminPayrollPageRO> page, @Param("dto") TeamOfficeQueryAdminPayrollPageDTO dto);
    void updateBenefitUsingEmployeeBenefit(@Param("dto") TeamOfficePayEmployeesSalaryDTO dto);
@Update("update office_payroll inner join office_employee on office_payroll.employee_id=office_employee.id inner join office_department on office_employee.department_id=office_department.id set office_payroll.employee_department=office_department.title where office_payroll.employee_department is null and office_payroll.oasis_id=#{oasisId}")
    void updateDepartmentByOasisId(@Param("oasisId") String oasisId);
@Update("update office_payroll set net_pay=gross_pay-deductions-withhold_and_remit_tax where oasis_id=#{oasisId} and category='2' and net_pay is null")
    void updateNetPayByOasisId(@Param("oasisId") String oasisId);

    IPage<TeamOfficeQueryEmployeePayrollPageRO> selectEmployeePayrollPageByQ(IPage<TeamOfficeQueryEmployeePayrollPageRO> page, @Param("dto") TeamOfficeQueryEmployeePayrollPageDTO dto,@Param("employeeBrandId") String employeeBrandId);

    TeamOfficeFetchPayrollInfoRO selectOnePayrollById(@Param("id") String id);

}
