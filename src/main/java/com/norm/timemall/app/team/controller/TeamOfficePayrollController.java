package com.norm.timemall.app.team.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchDashboardRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchPayrollInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryAdminPayrollPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryEmployeePayrollPageRO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeFetchDashboardVO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeFetchPayrollInfoVO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeQueryAdminPayrollPageVO;
import com.norm.timemall.app.team.domain.vo.TeamOfficeQueryEmployeePayrollPageVO;
import com.norm.timemall.app.team.service.TeamOfficePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamOfficePayrollController {
    @Autowired
    private TeamOfficePayrollService teamOfficePayrollService;


    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/team/office/payroll/admin_query")
    public TeamOfficeQueryAdminPayrollPageVO queryAdminPayrolls(@Validated TeamOfficeQueryAdminPayrollPageDTO dto){
        IPage<TeamOfficeQueryAdminPayrollPageRO> payroll=teamOfficePayrollService.findPayrolls(dto);
        TeamOfficeQueryAdminPayrollPageVO vo = new TeamOfficeQueryAdminPayrollPageVO();
        vo.setPayroll(payroll);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/team/office/payroll/dashboard")
    public TeamOfficeFetchDashboardVO fetchDashboard(String oasisId){
        TeamOfficeFetchDashboardRO dashboard = teamOfficePayrollService.findDashboard(oasisId);
        TeamOfficeFetchDashboardVO vo =new TeamOfficeFetchDashboardVO();
        vo.setDashboard(dashboard);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @DeleteMapping("/api/v1/team/office/payroll/{id}/del")
    public SuccessVO removePayroll(@PathVariable("id") String id){
        teamOfficePayrollService.delOnePayroll(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/team/office/payroll/create_perks")
    public SuccessVO givePerk(@Validated @RequestBody TeamOfficeGivePerkDTO dto){
        teamOfficePayrollService.doGenerateGivePerkPayroll(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/team/office/payroll/create_by_status")
    public SuccessVO payEmployeesSalary(@Validated @RequestBody TeamOfficePayEmployeesSalaryDTO dto){
        teamOfficePayrollService.doBatchGenerateEmployeesSalaryPayroll(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/team/office/payroll/create_one")
    public SuccessVO payOneEmployeeSalary(@Validated @RequestBody TeamOfficePayOneEmployeeSalaryDTO dto){
        teamOfficePayrollService.doGenerateOneEmployeeSalaryPayroll(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/team/office/payroll/employee_query")
    public TeamOfficeQueryEmployeePayrollPageVO queryEmployeePayrollPageVO(@Validated TeamOfficeQueryEmployeePayrollPageDTO dto){
        IPage<TeamOfficeQueryEmployeePayrollPageRO> payroll = teamOfficePayrollService.findEmployeePayrolls(dto);
        TeamOfficeQueryEmployeePayrollPageVO vo = new TeamOfficeQueryEmployeePayrollPageVO();
        vo.setPayroll(payroll);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/team/office/payroll/{id}/info")
    public TeamOfficeFetchPayrollInfoVO fetchPayrollInfo(@PathVariable("id") String id){
        TeamOfficeFetchPayrollInfoRO payroll = teamOfficePayrollService.findPayrollInfo(id);
        TeamOfficeFetchPayrollInfoVO vo  = new TeamOfficeFetchPayrollInfoVO();
        vo.setPayroll(payroll);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/team/office/payroll/benefit/edit")
    public SuccessVO editPayrollBenefit(@Validated @RequestBody TeamOfficeEditPayrollBenefitDTO dto){
        teamOfficePayrollService.doEditPayrollBenefit(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/team/office/payroll/compensation/create")
    public SuccessVO addPayrollCompensation(@Validated @RequestBody TeamOfficeAddPayrollCompensationDTO dto){
        teamOfficePayrollService.doAddPayrollCompensation(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/office/payroll/compensation/change")
    public SuccessVO editPayrollCompensation(@Validated @RequestBody TeamOfficeEditPayrollCompensationDTO dto){

        teamOfficePayrollService.doEditPayrollCompensation(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/team/office/payroll/{id}/pay")
    public SuccessVO payPayroll(@PathVariable("id") String id){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PAY_OASIS_EMPLOYEE.getMark());

            teamOfficePayrollService.doPayPayroll(id);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PAY_OASIS_EMPLOYEE.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
