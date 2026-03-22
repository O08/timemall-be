package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodGetSubscriptionPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodGetSubscriptionPageRO;
import com.norm.timemall.app.pod.domain.vo.PodGetSubscriptionPageVO;
import com.norm.timemall.app.pod.service.PodSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PodSubscriptionController {
    @Autowired
    private PodSubscriptionService podSubscriptionService;
    @GetMapping("/api/v1/web_epod/subscription/query")
    public PodGetSubscriptionPageVO fetchSubscription(@Validated PodGetSubscriptionPageDTO dto){
        IPage<PodGetSubscriptionPageRO> subscription=podSubscriptionService.findSubscription(dto);
        PodGetSubscriptionPageVO vo = new PodGetSubscriptionPageVO();
        vo.setSubscription(subscription);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/web_epod/subscription/{id}/cancel")
    public SuccessVO cancelSubscription(@PathVariable("id")String id){

        podSubscriptionService.cancel(id);
        return new SuccessVO(CodeEnum.SUCCESS);

   }
}
