package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubscriptionPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubscriptionPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSubscriptionPageVO;
import com.norm.timemall.app.studio.service.StudioSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioSubscriptionController {


    @Autowired
    private StudioSubscriptionService studioSubscriptionService;

    @GetMapping("/api/v1/web_estudio/brand/subscription/query")
    public StudioGetSubscriptionPageVO getSubscriptions(@Validated StudioGetSubscriptionPageDTO dto){

        IPage<StudioGetSubscriptionPageRO> subscription=studioSubscriptionService.fetchSubscriptions(dto);
        StudioGetSubscriptionPageVO vo = new StudioGetSubscriptionPageVO();
        vo.setSubscription(subscription);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

}
