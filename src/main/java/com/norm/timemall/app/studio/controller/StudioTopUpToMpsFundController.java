package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioTopUpToMpsFundDTO;
import com.norm.timemall.app.studio.service.StudioMpsFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudioTopUpToMpsFundController {
    @Autowired
    private StudioMpsFundService studioMpsFundService;
    @Autowired
    private OrderFlowService orderFlowService;
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/mps/top_up_to_fund")
    public SuccessVO topUpToMpsFund( @RequestBody @Validated  StudioTopUpToMpsFundDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.TOPUP_MPS_FUND.getMark());
            studioMpsFundService.topUpToMpsFund(dto.getAmount() ,dto.getMpsfundId());
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.TOPUP_MPS_FUND.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/web_estudio/mps_fund/apply_account")
    public SuccessVO applyMpsFundAccount(){
        studioMpsFundService.applyMpsFundAccount();
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
