package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import com.norm.timemall.app.pod.domain.vo.PodCellPlanOrderPageVO;
import com.norm.timemall.app.pod.service.PodCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodCellPlanOrderController {
    @Autowired
    private PodCellPlanOrderService podCellPlanOrderService;

    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/cell/plan_order")
    public PodCellPlanOrderPageVO retrieveCellPlanOrders(@Validated FetchCellPlanOrderPageDTO dto){

        IPage<PodCellPlanOrderPageRO> planOrder=podCellPlanOrderService.findCellPlanOrderPage(dto);
        PodCellPlanOrderPageVO vo = new PodCellPlanOrderPageVO();
        vo.setPlanOrder(planOrder);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
