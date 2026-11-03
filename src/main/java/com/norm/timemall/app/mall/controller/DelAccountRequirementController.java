package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.pojo.FetchDelAccountRequirement;
import com.norm.timemall.app.mall.domain.vo.FetchDelAccountRequirementVO;
import com.norm.timemall.app.mall.service.DelAccountRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelAccountRequirementController {
    @Autowired
    private DelAccountRequirementService delAccountRequirementService;
    @GetMapping("/api/v1/web_mall/me/del_account_requirements")
    public FetchDelAccountRequirementVO fetchDelAccountRequirement(){

        FetchDelAccountRequirement requirement = delAccountRequirementService.findRequirement();
        FetchDelAccountRequirementVO vo=new FetchDelAccountRequirementVO();
        vo.setRequirement(requirement);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
