package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamAcceptOasisTaskDTO;
import com.norm.timemall.app.team.domain.dto.TeamCommissionDTO;
import com.norm.timemall.app.team.domain.dto.TeamFinishOasisTask;
import com.norm.timemall.app.team.domain.dto.TeamOasisNewTaskDTO;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import com.norm.timemall.app.team.domain.vo.TeamCommissionPageVO;
import com.norm.timemall.app.team.service.TeamCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamCommissionController {
    @Autowired
    private TeamCommissionService teamCommissionService;
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
        teamCommissionService.acceptOasisTask(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/commission/finish")
    public SuccessVO finishTask(@Validated @RequestBody TeamFinishOasisTask dto){
        teamCommissionService.finishOasisTask(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
