package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.enums.VirtualOrderTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderMaintenanceDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVirtualOrderListPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchVirtualOrderListPageVO;
import com.norm.timemall.app.studio.service.StudioVirtualProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioVirtualProductOrderController {
    @Autowired
    private StudioVirtualProductOrderService studioVirtualProductOrderService;

    @Autowired
    private OrderFlowService orderFlowService;
    @GetMapping("/api/v1/web_estudio/virtual/order/list")
    public StudioFetchVirtualOrderListPageVO fetchVirtualOrderList(@Validated StudioFetchVirtualOrderListPageDTO dto){

        IPage<StudioFetchVirtualOrderListPageRO> order= studioVirtualProductOrderService.findOrders(dto);
        StudioFetchVirtualOrderListPageVO vo = new StudioFetchVirtualOrderListPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PostMapping("/api/v1/web_estudio/virtual/order/{order_id}/remittance")
    public SuccessVO orderRemittance(@PathVariable("order_id") String orderId){

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_REMITTANCE_TO_SELLER.getMark());
            studioVirtualProductOrderService.fromPlatformRemittanceToSeller(orderId);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.VIRTUAL_REMITTANCE_TO_SELLER.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/brand/virtual/product/order/maintenance")
    public SuccessVO orderMaintenance(@Validated @RequestBody StudioFetchVirtualOrderMaintenanceDTO dto){

        // only support COMPLETED,CANCELLED,DELIVERING
        boolean isSupportTag = (""+VirtualOrderTagEnum.COMPLETED.ordinal()).equals(dto.getTag())
                 || (""+VirtualOrderTagEnum.CANCELLED.ordinal()).equals(dto.getTag())
                || (""+VirtualOrderTagEnum.DELIVERING.ordinal()).equals(dto.getTag());

        if(!isSupportTag){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        studioVirtualProductOrderService.orderMaintenance(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
