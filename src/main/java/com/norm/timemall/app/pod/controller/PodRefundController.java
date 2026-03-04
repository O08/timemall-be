package com.norm.timemall.app.pod.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pod.domain.dto.PodRefundDTO;
import com.norm.timemall.app.pod.service.PodRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodRefundController {
    @Autowired
    private PodRefundService podRefundService;
    @Autowired
    private OrderFlowService orderFlowService;
    @PostMapping("/api/v1/web_epod/refund")
    public SuccessVO refund(@Validated @RequestBody PodRefundDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.REFUND.getMark());
            podRefundService.refund(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.REFUND.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
