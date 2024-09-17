package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommissionWsRoleEnum;
import com.norm.timemall.app.base.enums.OasisCommissionTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDetail;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import com.norm.timemall.app.team.domain.vo.TeamCommissionPageVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchCommissionDetailVO;
import com.norm.timemall.app.team.service.TeamApiAccessControlService;
import com.norm.timemall.app.team.service.TeamCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamCommissionController {
    @Autowired
    private TeamCommissionService teamCommissionService;
    @Autowired
    private OrderFlowService orderFlowService;
    @Autowired
    private TeamApiAccessControlService teamApiAccessControlService;
    /**
     * oasis 任务列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/commission")
    public TeamCommissionPageVO retrieveCommission(@Validated TeamCommissionDTO dto){
        IPage<TeamCommissionRO> commission =  teamCommissionService.findCommission(dto);
        TeamCommissionPageVO vo = new TeamCommissionPageVO();
        vo.setCommission(commission);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/commission")
    public SuccessVO newTask(@Validated @RequestBody TeamOasisNewTaskDTO dto){
        teamCommissionService.newOasisTask(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *   接取任务
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/commission/accept")
    public SuccessVO acceptTask(@Validated @RequestBody TeamAcceptOasisTaskDTO dto){
        String role=teamApiAccessControlService.findCommissionWsRole(dto.getCommissionId());
        if(CommissionWsRoleEnum.ADMIN.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        try{
            orderFlowService.insertOrderFlow(dto.getCommissionId(), OasisCommissionTagEnum.ACCEPT.getMark());
            teamCommissionService.acceptOasisTask(dto);
        }finally {
            orderFlowService.deleteOrderFlow(dto.getCommissionId(), OasisCommissionTagEnum.ACCEPT.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/commission/finish")
    public SuccessVO finishTask(@Validated @RequestBody TeamFinishOasisTaskDTO dto){
        String role=teamApiAccessControlService.findCommissionWsRole(dto.getCommissionId());
        if(!CommissionWsRoleEnum.ADMIN.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        try {
            orderFlowService.insertOrderFlow(dto.getCommissionId(), OasisCommissionTagEnum.FINISH.getMark());
            teamCommissionService.finishOasisTask(dto);
        }finally {
            orderFlowService.deleteOrderFlow(dto.getCommissionId(), OasisCommissionTagEnum.FINISH.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * oasis 任务明细
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/commission_ws/{id}/detail")
    public TeamFetchCommissionDetailVO fetchCommissionDetail(@PathVariable("id") String id){

        TeamFetchCommissionDetail detail =  teamCommissionService.findCommissionDetail(id);
        TeamFetchCommissionDetailVO vo = new TeamFetchCommissionDetailVO();
        vo.setDetail(detail);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }

    /**
     *   审核任务
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/commission/examine")
    public SuccessVO examineTask(@Validated @RequestBody TeamExamineOasisTaskDTO dto){

        boolean validated= OasisCommissionTagEnum.ABOLISH.getMark().equals(dto.getTag()) || OasisCommissionTagEnum.ADD_TO_NEED_POOL.getMark().equals(dto.getTag());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String role=teamApiAccessControlService.findCommissionWsRole(dto.getCommissionId());
        if(!CommissionWsRoleEnum.ADMIN.getMark().equals(role)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamCommissionService.examineOasisTask(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
