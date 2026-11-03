package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchDashboardRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchPayrollInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryAdminPayrollPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryEmployeePayrollPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOfficePayrollService {
    IPage<TeamOfficeQueryAdminPayrollPageRO> findPayrolls(TeamOfficeQueryAdminPayrollPageDTO dto);

    TeamOfficeFetchDashboardRO findDashboard(String oasisId);

    void delOnePayroll(String id);

    void doGenerateGivePerkPayroll(TeamOfficeGivePerkDTO dto);

    void doBatchGenerateEmployeesSalaryPayroll(TeamOfficePayEmployeesSalaryDTO dto);

    void doGenerateOneEmployeeSalaryPayroll(TeamOfficePayOneEmployeeSalaryDTO dto);

    IPage<TeamOfficeQueryEmployeePayrollPageRO> findEmployeePayrolls(TeamOfficeQueryEmployeePayrollPageDTO dto);

    TeamOfficeFetchPayrollInfoRO findPayrollInfo(String id);

    void doEditPayrollBenefit(TeamOfficeEditPayrollBenefitDTO dto);

    void doAddPayrollCompensation(TeamOfficeAddPayrollCompensationDTO dto);

    void doEditPayrollCompensation(TeamOfficeEditPayrollCompensationDTO dto);

    void doPayPayroll(String id);

}
