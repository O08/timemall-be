package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.BrandPromotionTagEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.BrandPromotion;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.studio.domain.dto.StudioRefreshPromotionDTO;
import com.norm.timemall.app.studio.domain.ro.FetchBrandPromotionRO;
import com.norm.timemall.app.studio.domain.ro.FetchCellCouponBenefitRO;
import com.norm.timemall.app.studio.mapper.StudioBrandPromotionMapper;
import com.norm.timemall.app.studio.mapper.StudioCellPlanOrderMapper;
import com.norm.timemall.app.studio.mapper.StudioOrderDetailsMapper;
import com.norm.timemall.app.studio.service.StudioBrandPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class StudioBrandPromotionServiceImpl implements StudioBrandPromotionService {
    @Autowired
    private StudioBrandPromotionMapper studioBrandPromotionMapper;
    @Autowired
    private StudioOrderDetailsMapper studioOrderDetailsMapper;
    @Autowired
    private StudioCellPlanOrderMapper studioCellPlanOrderMapper;

    @Override
    public FetchBrandPromotionRO findBrandPromotion() {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        FetchBrandPromotionRO ro= studioBrandPromotionMapper.selectPromotionByBrandId(brandId);
        return ro;

    }

    @Override
    public void setupBrandPromotionInfo() {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Integer initCnt=0;
        Integer initDiscount=95;
        Integer initCreditPoint=100;
        BrandPromotion promotion=new BrandPromotion();
        promotion.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setCreditPoint(initCreditPoint)
                .setCreditPointTag(BrandPromotionTagEnum.CLOSED.getMark())
                .setCreditPointCnt(initCnt)
                .setEarlyBirdDiscount(initDiscount)
                .setEarlyBirdDiscountTag(BrandPromotionTagEnum.CLOSED.getMark())
                .setEarlyBirdDiscountCnt(initCnt)
                .setRepurchaseDiscount(initDiscount)
                .setRepurchaseDiscountTag(BrandPromotionTagEnum.CLOSED.getMark())
                .setRepurchaseDiscountCnt(initCnt)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioBrandPromotionMapper.insert(promotion);


    }

    @Override
    public void modifyPromotion(StudioRefreshPromotionDTO dto) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        BrandPromotion promotion=new BrandPromotion();
        promotion
                .setCreditPoint(dto.getCreditPoint())
                .setCreditPointTag(dto.getCreditPointTag())
                .setRepurchaseDiscount(dto.getRepurchaseDiscount())
                .setRepurchaseDiscountTag(dto.getRepurchaseDiscountTag())
                .setEarlyBirdDiscount(dto.getEarlyBirdDiscount())
                .setEarlyBirdDiscountTag(dto.getEarlyBirdDiscountTag())
                .setModifiedAt(new Date());
        LambdaUpdateWrapper<BrandPromotion> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.eq(BrandPromotion::getBrandId,brandId);
        studioBrandPromotionMapper.update(promotion,lambdaUpdateWrapper);

    }

    @Override
    public FetchCellCouponBenefitRO findCouponBenefit(String cellId,String supplierBrandId) {
        // consumer info
        String consumerBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        String consumerUserId=SecurityUserHelper.getCurrentPrincipal().getUserId();
        // credit point rule
        BigDecimal creditPoint= studioBrandPromotionMapper.selectCouponCreditPointBenefit(supplierBrandId,consumerBrandId);

        // repurchase rule
        LambdaQueryWrapper<CellPlanOrder> brandPlanOrderLambdaQueryWrapper=Wrappers.lambdaQuery();
        brandPlanOrderLambdaQueryWrapper.eq(CellPlanOrder::getBrandId,supplierBrandId)
                .eq(CellPlanOrder::getConsumerId,consumerUserId);
        boolean brandExistsPlanOrder = studioCellPlanOrderMapper.exists(brandPlanOrderLambdaQueryWrapper);

        LambdaQueryWrapper<OrderDetails> brandOrderDetailsLambdaQueryWrapper=Wrappers.lambdaQuery();
        brandOrderDetailsLambdaQueryWrapper.eq(OrderDetails::getBrandId,supplierBrandId)
                .eq(OrderDetails::getConsumerId,consumerUserId);
        boolean brandCellExistsCellOrder = studioOrderDetailsMapper.exists(brandOrderDetailsLambdaQueryWrapper);
        String canUseRepurchaseCoupon = (brandCellExistsCellOrder || brandExistsPlanOrder) ? "1" : "0";

        // early bird rule
        LambdaQueryWrapper<CellPlanOrder> planOrderLambdaQueryWrapper=Wrappers.lambdaQuery();
        planOrderLambdaQueryWrapper.eq(CellPlanOrder::getCellId,cellId)
                .eq(CellPlanOrder::getConsumerId,consumerUserId);
        boolean cellExistsPlanOrder = studioCellPlanOrderMapper.exists(planOrderLambdaQueryWrapper);
        LambdaQueryWrapper<OrderDetails> orderDetailsLambdaQueryWrapper=Wrappers.lambdaQuery();
        orderDetailsLambdaQueryWrapper.eq(OrderDetails::getCellId,cellId)
                .eq(OrderDetails::getConsumerId,consumerUserId);
        boolean cellExistsCellOrder = studioOrderDetailsMapper.exists(orderDetailsLambdaQueryWrapper);
        String canUseEarlyBirdCoupon= (!cellExistsPlanOrder && !cellExistsCellOrder) ? "1" : "0";

        FetchCellCouponBenefitRO ro = new FetchCellCouponBenefitRO();
        ro.setCreditPoint(creditPoint);
        ro.setCanUseRepurchaseCoupon(canUseRepurchaseCoupon);
        ro.setCanUseEarlyBirdCoupon(canUseEarlyBirdCoupon);
        return ro;

    }
}
