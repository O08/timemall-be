package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.studio.domain.dto.StudioTopUpDTO;
import com.norm.timemall.app.studio.mapper.StudioFinAccountMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingOrderMapper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.studio.service.StudioTopUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
@Slf4j
@Service
public class StudioTopUpServiceImpl implements StudioTopUpService {
    @Autowired
    private OrderFlowService orderFlowService;
    @Autowired
    private StudioProprietaryTradingOrderMapper studioProprietaryTradingOrderMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StudioFinAccountMapper studioFinAccountMapper;
    @Override
    public NewOrderRO topUp(StudioTopUpDTO dto) {
        // verify
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        boolean verify =payVerify(user.getUserId(), OrderStatusEnum.CREATING.name());
        if(!verify){
            throw new ErrorCodeException(CodeEnum.PROCESSING);
        }
        Brand brand = accountService.findBrandInfoByUserId(user.getUserId());
        // new ProprietaryTradingOrder
        ProprietaryTradingOrder proprietaryTradingOrder=new ProprietaryTradingOrder();
        proprietaryTradingOrder.setId(IdUtil.simpleUUID())
                .setBrandId(brand.getId())
                .setCustomerId(user.getUserId())
                .setTradingId("prd-0002")
                .setQuantity(1)
                .setTotal(dto.getAmountBig().add(dto.getAmountLittle()))
                .setStatus(""+OrderStatusEnum.CREATING.ordinal())
                .setStatusDesc(OrderStatusEnum.CREATING.name())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioProprietaryTradingOrderMapper.insert(proprietaryTradingOrder);

        // return StudioNewOrderRO
        NewOrderRO ro = new NewOrderRO();
        ro.setMerchantOrderId(proprietaryTradingOrder.getId());
        ro.setMerchantUserId(user.getUserId());

        return ro;

    }

    @Override
    public void topUpPostHandler(String brandId, BigDecimal total) {
         studioFinAccountMapper.updateAddDrawableByFid(total,brandId, FidTypeEnum.BRAND.getMark());
    }

    private boolean payVerify(String id,String stage){
        // verify identity or order exists and debounce
        return orderFlowService.deleteOrderFlow(id,stage)> 0 ;

    }
}
