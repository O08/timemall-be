package com.norm.timemall.app.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.RetrievePlanPageDTO;
import com.norm.timemall.app.mall.domain.pojo.FetchCellPlan;
import com.norm.timemall.app.mall.domain.ro.RetrievePlanRO;
import com.norm.timemall.app.mall.domain.vo.FetchCellPlanVO;
import com.norm.timemall.app.mall.domain.vo.RetrievePlanPageVO;
import com.norm.timemall.app.mall.service.CellPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CellPlanController {
    @Autowired
    private CellPlanService cellPlanService;
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/services/{cell_id}/plan")
    public FetchCellPlanVO fetchCellPlan(@PathVariable("cell_id") String cellId){

        FetchCellPlan plan = cellPlanService.findCellPlan(cellId);
        FetchCellPlanVO vo = new FetchCellPlanVO();
        vo.setPlan(plan);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/plans")
    public RetrievePlanPageVO retrievePlans(@Validated RetrievePlanPageDTO dto){

        IPage<RetrievePlanRO> plans= cellPlanService.findCellPlans(dto);
        RetrievePlanPageVO vo = new RetrievePlanPageVO();
        vo.setPlans(plans);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }
}
