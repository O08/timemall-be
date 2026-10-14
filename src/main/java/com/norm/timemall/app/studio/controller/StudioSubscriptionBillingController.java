package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsBillingPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsBillingPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSubsBillingPageVO;
import com.norm.timemall.app.studio.service.StudioSubscriptionBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioSubscriptionBillingController {
    @Autowired
    private StudioSubscriptionBillingService studioSubscriptionBillingService;

    @GetMapping("/api/v1/web_estudio/brand/subscription/bill/query")
    public StudioGetSubsBillingPageVO getSubsBilling(@Validated StudioGetSubsBillingPageDTO dto){

        IPage<StudioGetSubsBillingPageRO> bill = studioSubscriptionBillingService.findBills(dto);
        StudioGetSubsBillingPageVO vo = new StudioGetSubsBillingPageVO();
        vo.setBill(bill);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }


}
