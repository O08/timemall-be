package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.ro.FetchBluvarrierRO;
import com.norm.timemall.app.mall.domain.vo.FetchBluvarrierVO;
import com.norm.timemall.app.mall.service.BluvarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BluvarrierController {
    @Autowired
    private BluvarrierService bluvarrierService;

    @GetMapping("/api/v1/web_mall/bluvarrier")
    public FetchBluvarrierVO fetchBluvarrier(){

        FetchBluvarrierVO vo = new FetchBluvarrierVO();
        FetchBluvarrierRO ro= bluvarrierService.findBluvarrier();
        vo.setBluvarrier(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
