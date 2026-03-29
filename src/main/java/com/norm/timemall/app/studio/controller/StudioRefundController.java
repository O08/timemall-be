package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioRefundDTO;
import com.norm.timemall.app.studio.service.StudioRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioRefundController {

    @Autowired
    private StudioRefundService studioRefundService;

    @Autowired
    private OrderFlowService orderFlowService;

    @PostMapping("/api/v1/web_estudio/order/refund")
    public SuccessVO orderRefund(@Validated @RequestBody StudioRefundDTO dto){

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.REFUND.getMark());
            studioRefundService.refund(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.REFUND.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
