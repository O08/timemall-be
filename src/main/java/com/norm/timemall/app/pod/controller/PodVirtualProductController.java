package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodFetchVirtualOrderPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodVirtualOrderApplyRefundDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchVirtualOrderPageRO;
import com.norm.timemall.app.pod.domain.vo.PodFetchVirtualOrderPageVO;
import com.norm.timemall.app.pod.domain.vo.PodGetVirtualOrderDeliverMaterialVO;
import com.norm.timemall.app.pod.service.PodVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PodVirtualProductController {

    @Autowired
    private PodVirtualProductService podVirtualProductService;

    @GetMapping(value = "/api/v1/web_pod/virtual/order/list")
    public PodFetchVirtualOrderPageVO fetchVirtualOrders(@Validated PodFetchVirtualOrderPageDTO dto){

        IPage<PodFetchVirtualOrderPageRO> order = podVirtualProductService.findOrderList(dto);
        PodFetchVirtualOrderPageVO vo = new PodFetchVirtualOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @GetMapping("/api/v1/web_pod/virtual/order/{id}/deliver")
    public PodGetVirtualOrderDeliverMaterialVO getVirtualOrderDeliverMaterial(@PathVariable("id") String id){

       return  podVirtualProductService.findOrderDeliverMaterial(id);

    }

    @PutMapping("/api/v1/web_pod/virtual/order/apply_refund")
    public SuccessVO applyRefund(@RequestBody @Validated PodVirtualOrderApplyRefundDTO dto){

        podVirtualProductService.applyRefund(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }


}
