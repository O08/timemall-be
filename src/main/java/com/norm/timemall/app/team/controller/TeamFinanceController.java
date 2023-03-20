package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriution;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import com.norm.timemall.app.team.domain.vo.TeamFinBoardVO;
import com.norm.timemall.app.team.domain.vo.TeamFinDistributionVO;
import com.norm.timemall.app.team.service.TeamFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamFinanceController {

    @Autowired
    private TeamFinanceService teamFinanceService;
    /**
     * 下单标的
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/team/obj/order")
    public SuccessVO orderObj(String objId){
        teamFinanceService.orderObj(objId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * 资金看板
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/finance_board")
    public TeamFinBoardVO kanban(){
        TeamFinBoardRO ro = teamFinanceService.kanban();
        TeamFinBoardVO vo = new TeamFinBoardVO();
        vo.setBillboard(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    /**
     * 资金分布
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/finance_distribute")
    public TeamFinDistributionVO retrieveFinDistribution(){
        TeamFinDistriution ro = teamFinanceService.findFinDistribution();
        TeamFinDistributionVO vo = new TeamFinDistributionVO();
        vo.setDistribution(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }

}
