package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioGetShoppingSubscriptionPlansRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSpaceSubscriptionPlanPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsPlanPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsPlanVO;
import com.norm.timemall.app.studio.domain.vo.StudioGetShoppingSubscriptionPlansVO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSpaceSubscriptionPlanPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSubsPlanPageVO;
import com.norm.timemall.app.studio.service.StudioSubscriptionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class StudioSubscriptionPlanController {
    @Autowired
    private StudioSubscriptionPlanService studioSubscriptionPlanService;

    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/web_estudio/brand/subscription/plan/query")
    public StudioGetSubsPlanPageVO getSubsPlans(@Validated StudioGetSubsPlanPageDTO dto){

        IPage<StudioGetSubsPlanPageRO> plan=studioSubscriptionPlanService.findPlans(dto);
        StudioGetSubsPlanPageVO vo = new StudioGetSubsPlanPageVO();
        vo.setPlan(plan);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web_estudio/brand/subscription/plan/new")
    public SuccessVO createNewSubsPlan(@Validated @RequestBody StudioNewSubsPlanDTO dto){

        studioSubscriptionPlanService.newPlan(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/subscription/plan/change")
    public SuccessVO changePlan(@Validated @RequestBody StudioChangeSubsPlanDTO dto){

        studioSubscriptionPlanService.modifyPlan(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/web_estudio/brand/subscription/plan/{id}/del")
    public SuccessVO delPlan(@PathVariable("id") String id){

        studioSubscriptionPlanService.delPlan(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/brand/subscription/plan/modify_status")
    public SuccessVO changeStatus(@Validated @RequestBody StudioChangeSubsPlanStatusDTO dto){
        studioSubscriptionPlanService.modifyPlanStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web_estudio/brand/subscription/plan/{id}/query")
    public StudioGetOneSubsPlanVO getOneSubsPlan(@PathVariable("id") String id){
      return studioSubscriptionPlanService.findOnePlan(id);
    }
    @GetMapping("/api/v1/web_estudio/brand/space/subscription/plan/query")
    public StudioGetSpaceSubscriptionPlanPageVO fetchSpaceSubscriptionPlan(@Validated StudioGetSpaceSubscriptionPlanPageDTO dto){
        IPage<StudioGetSpaceSubscriptionPlanPageRO> plan = studioSubscriptionPlanService.findSpacePlans(dto);
        StudioGetSpaceSubscriptionPlanPageVO vo = new StudioGetSpaceSubscriptionPlanPageVO();
        vo.setPlan(plan);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/public/shopping/subscription/plan/query")
    public StudioGetShoppingSubscriptionPlansVO getShoppingSubscriptionPlans(@Validated StudioGetShoppingSubscriptionPlansDTO dto){
        ArrayList<StudioGetShoppingSubscriptionPlansRO> plan = studioSubscriptionPlanService.findShoppingPlans(dto);
        StudioGetShoppingSubscriptionPlansVO vo =new StudioGetShoppingSubscriptionPlansVO();
        vo.setPlan(plan);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PostMapping("/api/v1/web_estudio/shopping/subscription/plan/subscribe")
    public SuccessVO subscribe(@Validated @RequestBody StudioNewSubscriptionDTO dto){
        try {

            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
           studioSubscriptionPlanService.newSubscription(dto);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
