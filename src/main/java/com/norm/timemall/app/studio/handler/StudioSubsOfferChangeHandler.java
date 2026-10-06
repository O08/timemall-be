package com.norm.timemall.app.studio.handler;

import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.SubsOfferTypeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsOffer;
import com.norm.timemall.app.studio.domain.dto.StudioChangeSubsOfferDTO;
import com.norm.timemall.app.studio.mapper.StudioSubsOfferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StudioSubsOfferChangeHandler {
    @Autowired
    private StudioSubsOfferMapper studioSubsOfferMapper;

    public SubsOffer generateOffer(StudioChangeSubsOfferDTO dto){
        SubsOffer offer = studioSubsOfferMapper.selectById(dto.getOfferId());
        if(offer==null){
            throw new QuickMessageException("未找到相关优惠数据,操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!sellerBrandId.equals(offer.getSellerBrandId())){
            throw new QuickMessageException("权限校验不通过，操作失败");
        }


        if(SubsOfferTypeEnum.FULL_ITEM_DISCOUNT_PROMO_CODE.getMark().equals(offer.getOfferType())){
            offer=doGenerateDiscountOffer(dto);
        }
        if(SubsOfferTypeEnum.FIRST_PERIOD_CASH_PROMO_CODE_SP.getMark().equals(offer.getOfferType())){
            offer=doGenerateFirstPeriodCashPromoCodeSpOffer(dto);
        }
        if(SubsOfferTypeEnum.FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP.getMark().equals(offer.getOfferType())){
            offer=doGenerateDiscountOffer(dto);
        }
        if(SubsOfferTypeEnum.PAY_YEARLY_DISCOUNT_COUPON_SP.getMark().equals(offer.getOfferType())){
            offer=doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(dto);
        }
        if(SubsOfferTypeEnum.PAY_QUARTERLY_DISCOUNT_COUPON_SP.getMark().equals(offer.getOfferType())){
            offer=doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(dto);
        }

        return offer;
    }

    private SubsOffer doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(StudioChangeSubsOfferDTO dto){
        if(ObjectUtil.isNull(dto.getDiscountPercentage())){
            throw new QuickMessageException("折扣为空，操作失败");
        }
        SubsOffer offer = new SubsOffer();
        offer.setId(dto.getOfferId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setDiscountPercentage(dto.getDiscountPercentage())
                .setModifiedAt(new Date());

        return offer;
    }
    private SubsOffer doGenerateFirstPeriodCashPromoCodeSpOffer(StudioChangeSubsOfferDTO dto){
        if(ObjectUtil.isNull(dto.getDiscountAmount())){
            throw new QuickMessageException("减免金额为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getCapacity())){
            throw new QuickMessageException("券总数量为空，操作失败");
        }
        SubsOffer offer = new SubsOffer();
        offer.setId(dto.getOfferId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setDiscountAmount(dto.getDiscountAmount())
                .setCapacity(dto.getCapacity())
                .setModifiedAt(new Date());

        return offer;
    }
    private SubsOffer doGenerateDiscountOffer(StudioChangeSubsOfferDTO dto){
        if(ObjectUtil.isNull(dto.getDiscountPercentage())){
            throw new QuickMessageException("折扣为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getCapacity())){
            throw new QuickMessageException("券总数量为空，操作失败");
        }
        SubsOffer offer = new SubsOffer();
        offer.setId(dto.getOfferId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setDiscountPercentage(dto.getDiscountPercentage())
                .setCapacity(dto.getCapacity())
                .setModifiedAt(new Date());

        return offer;
    }
}
