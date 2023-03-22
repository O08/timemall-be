package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.base.pojo.vo.NewOrderVO;
import com.norm.timemall.app.studio.domain.dto.StudioTopUpDTO;
import com.norm.timemall.app.studio.service.StudioTopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioTopUpController {
    @Autowired
    private StudioTopUpService studioTopUpService;
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/order/top_up")
    public NewOrderVO topUp(@Validated StudioTopUpDTO dto){
        NewOrderRO studioNewOrderRO = studioTopUpService.topUp(dto);
        NewOrderVO vo = new NewOrderVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setOrder(studioNewOrderRO);
        return vo;
    }

}
