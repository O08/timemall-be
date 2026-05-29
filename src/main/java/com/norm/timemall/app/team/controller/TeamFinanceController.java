package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.team.domain.dto.TeamFinDistributionDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import com.norm.timemall.app.team.domain.vo.TeamFinBoardVO;
import com.norm.timemall.app.team.domain.vo.TeamFinDistributionPageVO;
import com.norm.timemall.app.team.domain.vo.TeamOasisPointVO;
import com.norm.timemall.app.team.service.TeamFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Validated
@RestController
public class TeamFinanceController {

    @Autowired
    private TeamFinanceService teamFinanceService;
    @Autowired
    private OrderFlowService orderFlowService;

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
    public TeamFinDistributionPageVO retrieveFinDistribution(@Validated TeamFinDistributionDTO dto){
        IPage<TeamFinDistriutionItem> ro = teamFinanceService.findFinDistribution(dto);
        TeamFinDistributionPageVO vo = new TeamFinDistributionPageVO();
        vo.setDistribution(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    /**
     * oasis 奉献查询
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/point_in_oasis")
    public TeamOasisPointVO retrievePointInOasis(@RequestParam @NotBlank(message = "oasisId is required") String oasisId,
                                                @RequestParam @NotBlank(message = "brandId is required") String brandId){
        BigDecimal point = teamFinanceService.findPointInOasis(oasisId,brandId);
        TeamOasisPointVO vo = new TeamOasisPointVO();
        vo.setPoint(point);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    /**
     * oasis 奉献查询
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/point/query_use_channel")
    public TeamOasisPointVO retrievePointInOasisUseChannel(@RequestParam @NotBlank(message = "channel is required") String channel){
        BigDecimal point = teamFinanceService.findPointInOasisUseChannel(channel);
        TeamOasisPointVO vo = new TeamOasisPointVO();
        vo.setPoint(point);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }


    /**
     * oasis 资金
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis_finance_board")
    public TeamFinBoardVO oasisKanban(@RequestParam @NotBlank(message = "oasisId is required") String oasisId){
        TeamFinBoardRO ro = teamFinanceService.oasisKanban(oasisId);
        TeamFinBoardVO vo = new TeamFinBoardVO();
        vo.setBillboard(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
