package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.base.pojo.vo.NewOrderVO;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.domain.dto.StudioTopUpDTO;
import com.norm.timemall.app.studio.service.StudioTopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioTopUpController {
    @Autowired
    private StudioTopUpService studioTopUpService;
    @Autowired
    private OrderFlowService orderFlowService;
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/order/top_up")
    public NewOrderVO topUp(@Validated @RequestBody StudioTopUpDTO dto){
        NewOrderVO vo = new NewOrderVO();
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    OrderStatusEnum.CREATED.name());

            NewOrderRO studioNewOrderRO = studioTopUpService.topUp(dto);
            vo.setResponseCode(CodeEnum.SUCCESS);
            vo.setOrder(studioNewOrderRO);

        }catch (Exception e){
            throw new ErrorCodeException(CodeEnum.PROCESSING);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    OrderStatusEnum.CREATED.name());
        }


        return vo;
    }

}
