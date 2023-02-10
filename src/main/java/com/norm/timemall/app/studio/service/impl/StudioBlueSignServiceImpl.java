package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.OrderFlow;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.studio.domain.ro.StudioNewOrderRO;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingOrderMapper;
import com.norm.timemall.app.studio.service.OrderFlowService;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StudioBlueSignServiceImpl implements StudioBlueSignService {
    @Autowired
    private StudioProprietaryTradingMapper tradingMapper;
    @Autowired
    private OrderFlowService orderFlowService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private StudioProprietaryTradingOrderMapper studioProprietaryTradingOrderMapper;

    @Autowired
    private StudioBrandMapper studioBrandMapper;
    @Override
    public StudioBlueSign findStudioBlueSign(String brandId) {
        // insert order flow statue machine creating this stage use user id replace order details id
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        List<OrderFlow> orderFlows = orderFlowService.lambdaQuery().eq(OrderFlow::getOrderId, user.getUserId())
                .eq(OrderFlow::getStage, OrderStatusEnum.CREATING.name()).list();
        if(orderFlows.size() == 0){
            orderFlowService.insertOrderFlow(user.getUserId(),OrderStatusEnum.CREATING.name());
        }
        return tradingMapper.selectBlueSign(brandId);
    }

    /**
     * 蓝标下单
     * @return
     */
    @Override
    public StudioNewOrderRO newBlueSignOrder() {
        // verify
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        boolean verify =payVerify(user.getUserId(),OrderStatusEnum.CREATING.name());
        if(!verify){
            throw new ErrorCodeException(CodeEnum.PROCESSING);
        }
        // select bluesign info
        Brand brand = accountService.findBrandInfoByUserId(user.getUserId());
        StudioBlueSign blueSign = tradingMapper.selectBlueSign(brand.getId());

        // new ProprietaryTradingOrder
        ProprietaryTradingOrder proprietaryTradingOrder=new ProprietaryTradingOrder();
        proprietaryTradingOrder.setId(IdUtil.simpleUUID())
                .setBrandId(brand.getId())
                .setCustomerId(user.getUserId())
                .setTradingId(blueSign.getId())
                .setQuantity(1)
                .setTotal(NumberUtil.toBigDecimal(blueSign.getPrice()))
                .setStatus(""+OrderStatusEnum.CREATING.ordinal())
                .setStatusDesc(OrderStatusEnum.CREATING.name())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioProprietaryTradingOrderMapper.insert(proprietaryTradingOrder);

        // return StudioNewOrderRO
        StudioNewOrderRO ro = new StudioNewOrderRO();
        ro.setMerchantOrderId(proprietaryTradingOrder.getId());
        ro.setMerchantUserId(user.getUserId());

        return ro;
    }
    /**
     * verify order status
     * @param id
     */
    @Override
    public boolean payVerify(String id,String stage){
        // verify identity or order exists and debounce
        return orderFlowService.deleteOrderFlow(id,stage)> 0 ;

    }

    @Override
    public void enableBlueSign() {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        studioBrandMapper.updateBlueSignByUserId(user.getUserId());
    }
}
