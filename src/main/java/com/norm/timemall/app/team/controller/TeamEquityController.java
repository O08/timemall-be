package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.OasisEquityOrder;
import com.norm.timemall.app.base.mo.OasisEquityPeriod;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityPeriodPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorOrderPageRO;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamEquityService;
import com.norm.timemall.app.base.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamEquityController {
    @Autowired
    private TeamEquityService teamEquityService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;


    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/team/oasis/equity/period/query")
    public TeamFetchEquityPeriodPageVO fetchEquityPeriods(@Validated TeamFetchEquityPeriodPageDTO dto){
        // validate user is oasis admin
        boolean isAdmin = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if (!isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        IPage<TeamFetchEquityPeriodPageRO> periods = teamEquityService.fetchEquityPeriods(dto);
       TeamFetchEquityPeriodPageVO vo = new TeamFetchEquityPeriodPageVO();
       vo.setPeriod(periods);
       vo.setResponseCode(CodeEnum.SUCCESS);
       return vo;
    }

    @GetMapping("/api/v1/team/oasis/{id}/equity/latest_period/summary")
    public TeamFetchLatestPeriodEquitySummaryVO fetchLatestPeriodEquitySummary(@PathVariable("id") String oasisId){
        // 查询数据前需校验用户为oasis admin role
        boolean isAdmin = teamDataPolicyService.passIfBrandIsCreatorOfOasis(oasisId);
        if (!isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        TeamFetchLatestPeriodEquitySummaryVO vo = teamEquityService.fetchLatestPeriodEquitySummary(oasisId);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/team/oasis/equity/register")
    public SuccessVO registerNewPeriodEquity(@RequestBody @Validated TeamRegisterNewPeriodEquityDTO dto){
        // 需校验用户为oasis admin role 才能继续使用本Api
        boolean isAdmin = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if (!isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        
        teamEquityService.registerNewPeriodEquity(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/team/oasis/equity/orders/query")
    public TeamFetchEquityOrderPageVO fetchEquityOrders(@Validated TeamFetchEquityOrderPageDTO dto){
        // 查询数据前需校验用户为oasis admin role
        boolean isAdmin = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if (!isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        IPage<TeamFetchEquityOrderPageRO> orders = teamEquityService.fetchEquityOrders(dto);
        TeamFetchEquityOrderPageVO vo = new TeamFetchEquityOrderPageVO();
        vo.setOrder(orders);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PostMapping("/api/v1/team/oasis/equity/orders/{id}/redeem")
    public SuccessVO redeemEquity(@PathVariable("id") String orderId){
        // 需校验用户为oasis admin role 才能继续使用本Api
        // First query the order to get oasisId
        OasisEquityOrder order = teamEquityService.getEquityOrderById(orderId);
        if (order == null) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        
        // Get oasisId from equity period
        OasisEquityPeriod period = teamEquityService.getEquityPeriodById(order.getEquityPeriodId());
        if (period == null) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        
        boolean isAdmin = teamDataPolicyService.passIfBrandIsCreatorOfOasis(period.getOasisId());
        if (!isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        String flowKey = "E_SINGLE";
        try {
            // Insert order flow to prevent concurrent clicks
            orderFlowService.insertOrderFlow(orderId, flowKey);
            teamEquityService.redeemEquity(orderId);
            return new SuccessVO(CodeEnum.SUCCESS);
        } finally {
            // Delete order flow after completion
            orderFlowService.deleteOrderFlow(orderId, flowKey);
        }
    }

    @PostMapping("/api/v1/team/oasis/equity/orders/{id}/write_off")
    public SuccessVO writeOffEquity(@PathVariable("id") String orderId){

        String flowKey = "E_SINGLE";
        try {
            // Insert order flow to prevent concurrent clicks
            orderFlowService.insertOrderFlow(orderId, flowKey);
            teamEquityService.writeOffEquity(orderId);
            return new SuccessVO(CodeEnum.SUCCESS);
        } finally {
            // Delete order flow after completion
            orderFlowService.deleteOrderFlow(orderId, flowKey);
        }
    }


    @PostMapping("/api/v1/team/oasis/equity/latest_period/sponsorship/buy")
    public SuccessVO buyEquity(@Validated @RequestBody TeamBuyEquityDTO dto){


        String flowKey = "B_EQUITY";
        
        try {
            // Insert order flow to prevent concurrent clicks
            orderFlowService.insertOrderFlow(dto.getPeriodId(), flowKey);
            teamEquityService.buyEquity(dto);
            return new SuccessVO(CodeEnum.SUCCESS);
        } finally {
            // Delete order flow after completion
            orderFlowService.deleteOrderFlow(dto.getPeriodId(), flowKey);
        }
    }


    /**
     * 查询支持者、粉丝处于持有状态的权益订单
     */
    @GetMapping("/api/v1/team/oasis/equity/orders/sponsor/query")
    public TeamFetchEquitySponsorOrderPageVO fetchEquityOrdersForSponsor(@Validated TeamFetchEquitySponsorOrderPageDTO dto){
        IPage<TeamFetchEquitySponsorOrderPageRO> orders = teamEquityService.fetchEquityOrdersForSponsor(dto);
        TeamFetchEquitySponsorOrderPageVO vo = new TeamFetchEquitySponsorOrderPageVO();
        vo.setOrder(orders);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/v1/team/oasis/{id}/equity/latest_period/sponsorship/info")
    public TeamFetchEquitySponsorshipInfoVO fetchEquitySponsorshipInfo(@PathVariable("id") String oasisId){
        TeamFetchEquitySponsorshipInfoVO vo = teamEquityService.fetchEquitySponsorshipInfo(oasisId);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }



}
