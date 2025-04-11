package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.team.domain.dto.TeamOasisAdminWithdrawDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisCollectAccountDTO;
import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import com.norm.timemall.app.team.service.TeamOasisCollectAccountService;
import com.norm.timemall.app.team.service.TeamOasisPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamOasisPayController {
    @Autowired
    private TeamOasisPayService teamOasisPayService;
    @Autowired
    private TeamOasisCollectAccountService teamOasisCollectAccountService;
    @Autowired
    private OrderFlowService orderFlowService;
    /**
     * oasis top up
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/team/top_up_to_oasis")
    public SuccessVO topUptoOasis(@Validated @RequestBody TeamTopUpOasisDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.TOPUP_OASIS.getMark());
            teamOasisPayService.topUptoOasis(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.TOPUP_OASIS.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * oasis collect account
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/oasis/collect_account")
    public SuccessVO collectAccountFromOasis( @Validated @RequestBody TeamOasisCollectAccountDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.OASIS_COLLECT_IN.getMark());
            teamOasisCollectAccountService.collectAccountFromOasis(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.OASIS_COLLECT_IN.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/team/oasis/admin_withdraw")
    public SuccessVO adminWithdrawFromOasis(@Validated @RequestBody TeamOasisAdminWithdrawDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.OASIS_ADMIN_WITHDRAW.getMark());
            teamOasisCollectAccountService.adminWithdrawFromOasis(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.OASIS_ADMIN_WITHDRAW.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    

}
