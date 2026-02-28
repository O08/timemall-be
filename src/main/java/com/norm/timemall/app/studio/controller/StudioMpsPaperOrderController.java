package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioMpsOrderReceivingDTO;
import com.norm.timemall.app.studio.service.StudioCommercialPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioMpsPaperOrderController {
    @Autowired
    private StudioCommercialPaperService studioCommercialPaperService;
    @Autowired
    private OrderFlowService orderFlowService;

    /**
     * 接受 mps paper 订单
     */
    @ResponseBody
    @PostMapping("/api/v1/web_estudio/mps_paper/order_receiving")
    public SuccessVO mpsOrderReceiving(@RequestBody @Validated StudioMpsOrderReceivingDTO dto){
        try {
            orderFlowService.insertOrderFlow(dto.getPaperId(), CommercialPaperTagEnum.PUBLISH.getMark());
            studioCommercialPaperService.mpsPaperOrderReceiving(dto);
        }finally {
            orderFlowService.deleteOrderFlow(dto.getPaperId(), CommercialPaperTagEnum.PUBLISH.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
