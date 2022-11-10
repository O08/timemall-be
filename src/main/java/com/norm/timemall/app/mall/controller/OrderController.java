package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.mall.domain.dto.OrderDTO;
import com.norm.timemall.app.mall.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderDetailService orderDetailService;
    /*
     * 下单
     */
    @ResponseBody
    @PostMapping(value = "/api/v1/web_mall/services/{cell_id}/order")
    public SuccessVO retrieveCellIntro(@AuthenticationPrincipal CustomizeUser userDetails,
                                       @PathVariable("cell_id") String cellId, @Validated @RequestBody OrderDTO orderDTO)
    {
        orderDetailService.newOrder(userDetails,cellId, orderDTO);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
