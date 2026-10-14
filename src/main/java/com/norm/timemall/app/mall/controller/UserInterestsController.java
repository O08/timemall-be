package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.pojo.FetchUserInterests;
import com.norm.timemall.app.mall.domain.vo.FetchUserInterestsVO;
import com.norm.timemall.app.mall.service.CustomerInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInterestsController {
    @Autowired
    private CustomerInterestService customerInterestService;
    @GetMapping("/api/v1/web_mall/me/interests")
    public FetchUserInterestsVO fetchUserInterests(){

        FetchUserInterests interest=customerInterestService.findInterest();
        FetchUserInterestsVO vo= new FetchUserInterestsVO();
        vo.setInterest(interest);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }
}
