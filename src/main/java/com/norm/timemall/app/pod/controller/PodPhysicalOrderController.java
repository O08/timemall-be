package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderPageRO;
import com.norm.timemall.app.pod.domain.vo.PodFetchPhysicalOrderDetailVO;
import com.norm.timemall.app.pod.domain.vo.PodFetchPhysicalOrderPageVO;
import com.norm.timemall.app.pod.service.PodPhysicalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodPhysicalOrderController {
    @Autowired
    private PodPhysicalOrderService physicalOrderService;

    @GetMapping("/api/v1/web_pod/physical_order/query")
    public PodFetchPhysicalOrderPageVO fetchPhysicalOrderPage(PodFetchPhysicalOrderPageDTO dto){
        IPage<PodFetchPhysicalOrderPageRO> order = physicalOrderService.findOrderList(dto);
        PodFetchPhysicalOrderPageVO vo = new PodFetchPhysicalOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_pod/physical_order/{order_id}/detail")
    public PodFetchPhysicalOrderDetailVO fetchPhysicalOrderDetail(@PathVariable("order_id") String orderId){
        PodFetchPhysicalOrderDetailRO detail = physicalOrderService.fetchOrderDetail(orderId);
        PodFetchPhysicalOrderDetailVO vo = new PodFetchPhysicalOrderDetailVO();
        vo.setDetail(detail);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
