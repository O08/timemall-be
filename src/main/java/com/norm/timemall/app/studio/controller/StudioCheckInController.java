package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCheckInInfoVO;
import com.norm.timemall.app.studio.service.StudioCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioCheckInController {
    @Autowired
    private StudioCheckInService studioCheckInService;

    /**
     * 获取品牌今日签到信息
     */
    @GetMapping("/api/v1/web_estudio/brand/check_in/info")
    public StudioFetchBrandCheckInInfoVO fetchBrandCheckInInfo(){
        return studioCheckInService.fetchBrandCheckInInfo();
    }
    /**
     * 品牌每日签到，签到成功赠送源能
     */
    @PostMapping("/api/v1/web_estudio/brand/check_in")
    public SuccessVO brandCheckIn(){
        return studioCheckInService.brandCheckIn();
    }
}