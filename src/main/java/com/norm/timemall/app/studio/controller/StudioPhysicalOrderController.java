package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioChangePhysicalOrderStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPhysicalOrderDeliveryDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDeliveryInfoRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchPhysicalOrderDeliveryInfoVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchPhysicalOrderDetailVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchPhysicalOrderPageVO;
import com.norm.timemall.app.studio.service.StudioPhysicalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioPhysicalOrderController {
    @Autowired
    private StudioPhysicalOrderService physicalOrderService;
    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/web_estudio/physical_order/query")
    public StudioFetchPhysicalOrderPageVO fetchPhysicalOrderPage(StudioFetchPhysicalOrderPageDTO dto){
        IPage<StudioFetchPhysicalOrderPageRO> order = physicalOrderService.fetchOrderPage(dto);
        StudioFetchPhysicalOrderPageVO vo = new StudioFetchPhysicalOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_estudio/physical_order/{order_id}/detail")
    public StudioFetchPhysicalOrderDetailVO fetchPhysicalOrderDetail(@PathVariable("order_id") String orderId){
        StudioFetchPhysicalOrderDetailRO detail = physicalOrderService.fetchOrderDetail(orderId);
        StudioFetchPhysicalOrderDetailVO vo = new StudioFetchPhysicalOrderDetailVO();
        vo.setDetail(detail);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/v1/web_estudio/physical_order/{order_id}/delivery_info")
    public StudioFetchPhysicalOrderDeliveryInfoVO fetchPhysicalOrderDeliveryInfo(@PathVariable("order_id") String orderId){
        StudioFetchPhysicalOrderDeliveryInfoRO info = physicalOrderService.fetchDeliveryInfo(orderId);
        StudioFetchPhysicalOrderDeliveryInfoVO vo = new StudioFetchPhysicalOrderDeliveryInfoVO();
        vo.setDelivery(info);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    // 发货
    @PutMapping("/api/v1/web_estudio/physical_order/delivery")
    public SuccessVO delivery(@Validated @RequestBody StudioPhysicalOrderDeliveryDTO dto){
        physicalOrderService.delivery(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/web_estudio/physical_order/{order_id}/remittances")
    public SuccessVO orderRemittance(@PathVariable("order_id") String orderId){
        physicalOrderService.remit(orderId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/web_estudio/physical_order/change_status")
    public SuccessVO changeOrderStatus(@Validated @RequestBody StudioChangePhysicalOrderStatusDTO dto){
        physicalOrderService.changeOrderStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
