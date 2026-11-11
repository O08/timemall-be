package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import com.norm.timemall.app.mall.domain.dto.MallOrderPhysicalProductDTO;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.domain.dto.OrderVirtualProductDTO;
import com.norm.timemall.app.mall.domain.vo.OrderCellPlanVO;
import com.norm.timemall.app.mall.domain.vo.OrderCellVO;
import com.norm.timemall.app.mall.domain.vo.OrderVirtualProductVO;
import com.norm.timemall.app.mall.handler.SendPhysicalOrderReceivingEmailNoticeHandler;
import com.norm.timemall.app.mall.service.CellPlanOrderService;
import com.norm.timemall.app.mall.service.MallPhysicalOrderService;
import com.norm.timemall.app.mall.service.OrderDetailService;
import com.norm.timemall.app.mall.service.MallVirtualProductOrderService;
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
    private MallVirtualProductOrderService mallVirtualProductOrderService;
    @Autowired
    private OrderFlowService orderFlowService;

    @Autowired
    private MallPhysicalOrderService mallPhysicalOrderService;

    @Autowired
    private SendPhysicalOrderReceivingEmailNoticeHandler sendPhysicalOrderReceivingEmailNoticeHandler;

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
    @PostMapping("/api/v1/mall/virtual/order")
    public OrderVirtualProductVO orderVirtualProduct(@RequestBody @Validated OrderVirtualProductDTO dto){

        OrderVirtualProductVO vo = new OrderVirtualProductVO();
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_PRODUCT_ORDER_PAY.getMark());
            String orderId = mallVirtualProductOrderService.newOrder(dto);
            vo.setOrderId(orderId);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_PRODUCT_ORDER_PAY.getMark());
        }
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PostMapping("/api/v1/mall/virtual/order/{id}/repay")
    public SuccessVO rePayVirtualProductOrder(@PathVariable("id") String orderId){

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_PRODUCT_ORDER_PAY.getMark());

            mallVirtualProductOrderService.repayOrder(orderId);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_PRODUCT_ORDER_PAY.getMark());
        }
        return  new SuccessVO(CodeEnum.SUCCESS);

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

    @PostMapping("/api/v1/mall/physical_product/new_order")
    public SuccessVO orderPhysicalProduct(@Validated @RequestBody MallOrderPhysicalProductDTO dto){
        String userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        try {
            orderFlowService.insertOrderFlow(userId, TransTypeEnum.PHYSICAL_ORDER_PAY.getMark());

            String orderId = mallPhysicalOrderService.newOrder(dto);
            mallPhysicalOrderService.executePayment(orderId);

            sendPhysicalOrderReceivingEmailNoticeHandler.sendNewOrderNotificationAsync(orderId);


            return new SuccessVO(CodeEnum.SUCCESS);
        } finally {
            orderFlowService.deleteOrderFlow(userId, TransTypeEnum.PHYSICAL_ORDER_PAY.getMark());
        }
    }
    @PostMapping("/api/v1/mall/physical_order/{order_id}/repay")
    public SuccessVO repayPhysicalOrder(@PathVariable("order_id") String orderId){
        String userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        try {
            orderFlowService.insertOrderFlow(userId, TransTypeEnum.PHYSICAL_ORDER_PAY.getMark());
            mallPhysicalOrderService.executePayment(orderId);
            sendPhysicalOrderReceivingEmailNoticeHandler.sendNewOrderNotificationAsync(orderId);
        }finally {
            orderFlowService.deleteOrderFlow(userId, TransTypeEnum.PHYSICAL_ORDER_PAY.getMark());
        }
        return  new SuccessVO(CodeEnum.SUCCESS);
    }

}