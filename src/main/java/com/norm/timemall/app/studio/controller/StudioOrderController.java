package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.ro.StudioNewOrderRO;
import com.norm.timemall.app.studio.domain.vo.StudioNewOrderVO;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioOrderController {
    @Autowired
    private StudioBlueSignService studioBlueSignService;
    /**
     *
     * 下单
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/order/new_order")
    public StudioNewOrderVO newOrder(String productCode){
        StudioNewOrderRO studioNewOrderRO = studioBlueSignService.newBlueSignOrder();
        StudioNewOrderVO vo = new StudioNewOrderVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setOrder(studioNewOrderRO);
        return vo;
    }

}
