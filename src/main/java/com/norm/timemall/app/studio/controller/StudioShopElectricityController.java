package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.ro.StudioGetElectricityProductInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetElectricityProductInfoVO;
import com.norm.timemall.app.studio.service.StudioElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioShopElectricityController {
    @Autowired
    private StudioElectricityService studioElectricityService;
    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/web_estudio/shop/electricity/query")
    public StudioGetElectricityProductInfoVO getStudioElectricityService() {

        StudioGetElectricityProductInfoRO product=studioElectricityService.findProductInfo();
        StudioGetElectricityProductInfoVO vo = new StudioGetElectricityProductInfoVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web_estudio/shop/electricity/buy")
    public SuccessVO buyElectricityProduct(){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.BUY_ELECTRICITY_PAY.getMark());
            studioElectricityService.buyElectricity();
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.BUY_ELECTRICITY_PAY.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
