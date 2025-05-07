package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.base.pojo.vo.NewOrderVO;
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
    public NewOrderVO newOrder(String productCode){
        NewOrderRO studioNewOrderRO = studioBlueSignService.newBlueSignOrder();
        NewOrderVO vo = new NewOrderVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setOrder(studioNewOrderRO);
        return vo;
    }

}
