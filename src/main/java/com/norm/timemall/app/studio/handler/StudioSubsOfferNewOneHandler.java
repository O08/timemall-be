package com.norm.timemall.app.studio.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.SubsClaimChannelEnum;
import com.norm.timemall.app.base.enums.SubsOfferStatusEnum;
import com.norm.timemall.app.base.enums.SubsOfferTypeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsOffer;
import com.norm.timemall.app.base.mo.SubsPlan;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.studio.domain.dto.StudioCreateNewSubsOfferDTO;
import com.norm.timemall.app.studio.mapper.StudioSubsOfferMapper;
import com.norm.timemall.app.studio.mapper.StudioSubsPlanMapper;
import com.norm.timemall.app.studio.mapper.StudioSubsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StudioSubsOfferNewOneHandler {

    @Autowired
    private StudioSubsOfferMapper studioSubsOfferMapper;

    @Autowired
    private StudioSubsProductMapper studioSubsProductMapper;

    @Autowired
    private StudioSubsPlanMapper studioSubsPlanMapper;

    public SubsOffer generateOffer(StudioCreateNewSubsOfferDTO dto){
        SubsOffer offer = new SubsOffer();
        if(SubsOfferTypeEnum.FULL_ITEM_DISCOUNT_PROMO_CODE.getMark().equals(dto.getOfferType())){
            offer=doGenerateFullItemDiscountPromoCodeOffer(dto);
        }
        if(SubsOfferTypeEnum.FIRST_PERIOD_CASH_PROMO_CODE_SP.getMark().equals(dto.getOfferType())){
            offer=doGenerateFirstPeriodCashPromoCodeSpOffer(dto);
        }
        if(SubsOfferTypeEnum.FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP.getMark().equals(dto.getOfferType())){
            offer=doGenerateFirstPeriodDiscountPercentagePromoCodeSpOffer(dto);
        }
        if(SubsOfferTypeEnum.PAY_YEARLY_DISCOUNT_COUPON_SP.getMark().equals(dto.getOfferType())){
            offer=doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(dto);
        }
        if(SubsOfferTypeEnum.PAY_QUARTERLY_DISCOUNT_COUPON_SP.getMark().equals(dto.getOfferType())){
            offer=doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(dto);
        }

        return offer;
    }

    private SubsOffer doGeneratePayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(StudioCreateNewSubsOfferDTO dto) {
        // validate param
        if(ObjectUtil.isNull(dto.getDiscountPercentage())){
            throw new QuickMessageException("折扣为空，操作失败");
        }

        if(CharSequenceUtil.isBlank(dto.getForProductId())){
            throw new QuickMessageException("商品参数为空，操作失败");
        }
        //validate product
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        SubsProduct product = studioSubsProductMapper.selectById(dto.getForProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品，操作失败");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }
         // validate offer can't repeat add
        LambdaQueryWrapper<SubsOffer> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getForProductId,dto.getForProductId())
                .eq(SubsOffer::getOfferType,dto.getOfferType());
        boolean existsOffer = studioSubsOfferMapper.exists(wrapper);
        if(existsOffer){
            throw new QuickMessageException("商品已经设配置相关优惠，操作失败");
        }
        return createPayPayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(dto);

    }




    private SubsOffer createPayPayYearlyOrQuarterlyDiscountPercentagePromoCouponSpOffer(StudioCreateNewSubsOfferDTO dto){

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        SubsOffer offer = new SubsOffer();
        offer.setId(IdUtil.simpleUUID())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setOfferType(dto.getOfferType())
                .setClaimChannel(SubsClaimChannelEnum.AUTO.getMark())
                .setForProductId(dto.getForProductId())
                .setDiscountPercentage(dto.getDiscountPercentage())
                .setSellerBrandId(sellerBrandId)
                .setStatus(SubsOfferStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());


        return  offer;
    }

    private SubsOffer doGenerateFirstPeriodDiscountPercentagePromoCodeSpOffer(StudioCreateNewSubsOfferDTO dto){
        // validate param
        if(CharSequenceUtil.isBlank(dto.getPromoCode())){
            throw new QuickMessageException("优惠码为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getDiscountPercentage())){
            throw new QuickMessageException("折扣为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getCapacity())){
            throw new QuickMessageException("券总数量为空，操作失败");
        }
        if(CharSequenceUtil.isBlank(dto.getForProductId())){
            throw new QuickMessageException("商品参数为空，操作失败");
        }
        // validate promo code
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<SubsOffer> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getPromoCode,dto.getPromoCode());
        wrapper.eq(SubsOffer::getSellerBrandId,sellerBrandId);

        SubsOffer dbOffer = studioSubsOfferMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(dbOffer)){
            throw new QuickMessageException("优惠码已注册，操作失败");
        }

        // validate product
        SubsProduct product = studioSubsProductMapper.selectById(dto.getForProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品，操作失败");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }
        SubsOffer offer = new SubsOffer();
        offer.setId(IdUtil.simpleUUID())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setOfferType(dto.getOfferType())
                .setClaimChannel(SubsClaimChannelEnum.KOL.getMark())
                .setPromoCode(dto.getPromoCode())
                .setDiscountPercentage(dto.getDiscountPercentage())
                .setForProductId(dto.getForProductId())
                .setSellerBrandId(sellerBrandId)
                .setCapacity(dto.getCapacity())
                .setStatus(SubsOfferStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        return  offer;


    }

    private SubsOffer doGenerateFirstPeriodCashPromoCodeSpOffer(StudioCreateNewSubsOfferDTO dto){
        // validate param
        if(CharSequenceUtil.isBlank(dto.getPromoCode())){
            throw new QuickMessageException("优惠码为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getDiscountAmount())){
            throw new QuickMessageException("减免金额为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getCapacity())){
            throw new QuickMessageException("券总数量为空，操作失败");
        }
        if(CharSequenceUtil.isBlank(dto.getForProductId())){
            throw new QuickMessageException("商品参数为空，操作失败");
        }
        // validate promo code
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<SubsOffer> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getPromoCode,dto.getPromoCode());
        wrapper.eq(SubsOffer::getSellerBrandId,sellerBrandId);

        SubsOffer dbOffer = studioSubsOfferMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(dbOffer)){
            throw new QuickMessageException("优惠码已注册，操作失败");
        }

        // validate product
        SubsProduct product = studioSubsProductMapper.selectById(dto.getForProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品，操作失败");
        }
        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }
        SubsOffer offer = new SubsOffer();
        offer.setId(IdUtil.simpleUUID())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setOfferType(dto.getOfferType())
                .setClaimChannel(SubsClaimChannelEnum.KOL.getMark())
                .setPromoCode(dto.getPromoCode())
                .setDiscountAmount(dto.getDiscountAmount())
                .setForProductId(dto.getForProductId())
                .setSellerBrandId(sellerBrandId)
                .setCapacity(dto.getCapacity())
                .setStatus(SubsOfferStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        return  offer;


    }

    private SubsOffer doGenerateFullItemDiscountPromoCodeOffer(StudioCreateNewSubsOfferDTO dto){
        // validate
        if(CharSequenceUtil.isBlank(dto.getPromoCode())){
            throw new QuickMessageException("优惠码为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getDiscountPercentage())){
            throw new QuickMessageException("折扣为空，操作失败");
        }
        if(ObjectUtil.isNull(dto.getCapacity())){
            throw new QuickMessageException("券总数量为空，操作失败");
        }

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<SubsOffer> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getPromoCode,dto.getPromoCode());
        wrapper.eq(SubsOffer::getSellerBrandId,sellerBrandId);

        SubsOffer dbOffer = studioSubsOfferMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(dbOffer)){
            throw new QuickMessageException("优惠码已注册，操作失败");
        }

        SubsOffer offer = new SubsOffer();
        offer.setId(IdUtil.simpleUUID())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setOfferType(dto.getOfferType())
                .setClaimChannel(SubsClaimChannelEnum.KOL.getMark())
                .setPromoCode(dto.getPromoCode())
                .setDiscountPercentage(dto.getDiscountPercentage())
                .setSellerBrandId(sellerBrandId)
                .setCapacity(dto.getCapacity())
                .setStatus(SubsOfferStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        return  offer;


    }
}
