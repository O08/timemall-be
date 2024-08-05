package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.mall.domain.dto.MallFetchPromotionInfoDTO;
import com.norm.timemall.app.mall.domain.ro.CouponCreditPointBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import com.norm.timemall.app.mall.mapper.CellPlanOrderMapper;
import com.norm.timemall.app.mall.mapper.MallBrandPromotionMapper;
import com.norm.timemall.app.mall.mapper.OrderDetailsMapper;
import com.norm.timemall.app.mall.service.MallBrandPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MallBrandPromotionServiceImpl implements MallBrandPromotionService {
    @Autowired
    private MallBrandPromotionMapper mallBrandPromotionMapper;
    @Autowired
    private CellPlanOrderMapper cellPlanOrderMapper;
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;


    @Override
    public MallFetchPromotionInfoRO findPromotionInfo(MallFetchPromotionInfoDTO dto) {
        MallFetchPromotionInfoRO ro = mallBrandPromotionMapper.selectPromotionByBrandId(dto.getBrandId());
        return ro;
    }

    @Override
    public MallFetchPromotionBenefitRO findPromotionBenefit(String cellId, String supplierBrandId) {
        // consumer info
        String consumerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        String consumerUserId=SecurityUserHelper.getCurrentPrincipal().getUserId();
        // credit point rule
        CouponCreditPointBenefitRO creditPointBenefit= mallBrandPromotionMapper.selectCouponCreditPointBenefit(supplierBrandId,consumerBrandId);

        // repurchase rule
        LambdaQueryWrapper<CellPlanOrder> brandPlanOrderLambdaQueryWrapper= Wrappers.lambdaQuery();
        brandPlanOrderLambdaQueryWrapper.eq(CellPlanOrder::getBrandId,supplierBrandId)
                .eq(CellPlanOrder::getConsumerId,consumerUserId);
        boolean brandExistsPlanOrder = cellPlanOrderMapper.exists(brandPlanOrderLambdaQueryWrapper);

        LambdaQueryWrapper<OrderDetails> brandOrderDetailsLambdaQueryWrapper=Wrappers.lambdaQuery();
        brandOrderDetailsLambdaQueryWrapper.eq(OrderDetails::getBrandId,supplierBrandId)
                .eq(OrderDetails::getConsumerId,consumerUserId);
        boolean brandCellExistsCellOrder = orderDetailsMapper.exists(brandOrderDetailsLambdaQueryWrapper);
        String canUseRepurchaseCoupon = (brandCellExistsCellOrder || brandExistsPlanOrder) ? "1" : "0";

        // early bird rule
        LambdaQueryWrapper<CellPlanOrder> planOrderLambdaQueryWrapper=Wrappers.lambdaQuery();
        planOrderLambdaQueryWrapper.eq(CellPlanOrder::getCellId,cellId)
                .eq(CellPlanOrder::getConsumerId,consumerUserId);
        boolean cellExistsPlanOrder = cellPlanOrderMapper.exists(planOrderLambdaQueryWrapper);
        LambdaQueryWrapper<OrderDetails> orderDetailsLambdaQueryWrapper=Wrappers.lambdaQuery();
        orderDetailsLambdaQueryWrapper.eq(OrderDetails::getCellId,cellId)
                .eq(OrderDetails::getConsumerId,consumerUserId);
        boolean cellExistsCellOrder = orderDetailsMapper.exists(orderDetailsLambdaQueryWrapper);
        String canUseEarlyBirdCoupon= (!cellExistsPlanOrder && !cellExistsCellOrder) ? "1" : "0";

        MallFetchPromotionBenefitRO ro = new MallFetchPromotionBenefitRO();
        if(ObjectUtil.isNotNull(creditPointBenefit)){
            ro.setCreditPoint(creditPointBenefit.getCreditPoint());
            ro.setAlreadyGetCreditPoint(creditPointBenefit.getAlreadyGetCreditPoint());
        }
        ro.setCanUseRepurchaseCoupon(canUseRepurchaseCoupon);
        ro.setCanUseEarlyBirdCoupon(canUseEarlyBirdCoupon);
        return ro;
    }
}
