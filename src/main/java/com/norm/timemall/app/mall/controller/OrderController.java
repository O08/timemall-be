package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.domain.vo.OrderCellPlanVO;
import com.norm.timemall.app.mall.domain.vo.OrderCellVO;
import com.norm.timemall.app.mall.service.CellPlanOrderService;
import com.norm.timemall.app.mall.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CellPlanOrderService cellPlanOrderService;
    @Autowired
    private OrderFlowService orderFlowService;

    /*
     * 下单
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/mall/services/{cell_id}/order")
    public OrderCellVO orderCell(@AuthenticationPrincipal CustomizeUser userDetails,
                                         @PathVariable("cell_id") String cellId, @Validated @RequestBody OrderDTO orderDTO) {

        String orderId = orderDetailService.newOrder(userDetails, cellId, orderDTO);
        OrderCellVO vo = new OrderCellVO();
        vo.setOrderId(orderId);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @ResponseBody
    @PostMapping(value = "/api/v1/mall/services/plan/{id}/order")
    public OrderCellPlanVO orderPlan(@PathVariable("id") String planId, @RequestBody AffiliateDTO dto) {
        OrderCellPlanVO vo = new OrderCellPlanVO();

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PLAN_ORDER_PAY.getMark());
            String planOrderId = cellPlanOrderService.newOrder(planId,dto);
            vo.setPlanOrderId(planOrderId);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PLAN_ORDER_PAY.getMark());
        }
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PostMapping(value = "/api/v1/mall/plan/order/{id}/repay")
    public SuccessVO repayPlanOrder(@PathVariable("id") String orderId){

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PLAN_ORDER_PAY.getMark());

            cellPlanOrderService.repayOrder(orderId);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.PLAN_ORDER_PAY.getMark());
        }
        return  new SuccessVO(CodeEnum.SUCCESS);

    }

}