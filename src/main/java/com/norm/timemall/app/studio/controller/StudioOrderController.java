package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.base.pojo.vo.NewOrderVO;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioOrderController {
    @Autowired
    private StudioBlueSignService studioBlueSignService;

    @Autowired
    private OrderFlowService orderFlowService;
    /**
     *
     * 下单
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/order/new_order")
    public SuccessVO newOrder(){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.BUY_BLUE_VIP_PAY.getMark());
            studioBlueSignService.newBlueSignOrder();
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.BUY_BLUE_VIP_PAY.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
