package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.BrandPromotion;
import com.norm.timemall.app.mall.domain.ro.CouponCreditPointBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;


/**
 * (brand_promotion)数据Mapper
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MallBrandPromotionMapper extends BaseMapper<BrandPromotion> {
@Select("select credit_point,early_bird_discount,repurchase_discount,credit_point_tag,early_bird_discount_tag,repurchase_discount_tag from brand_promotion where brand_id=#{brandId}")
    MallFetchPromotionInfoRO selectPromotionByBrandId(@Param("brandId") String brandId);

    CouponCreditPointBenefitRO selectCouponCreditPointBenefit(@Param("supplierBrandId") String supplierBrandId,
                                                              @Param("consumerBrandId")String consumerBrandId);
    @Update("update brand_promotion set credit_point_cnt=credit_point_cnt+1 where brand_id=#{brandId}")
    void incrementCreditPointCnt(@Param("brandId") String brandId);

    @Update("update brand_promotion set early_bird_discount_cnt=early_bird_discount_cnt+1 where brand_id=#{brandId}")
    void incrementEarlyBirdDiscountCnt(@Param("brandId") String brandId);

    @Update("update  brand_promotion set repurchase_discount_cnt=repurchase_discount_cnt+1 where brand_id=#{brandId}")
    void incrementRepurchaseDiscountCnt(@Param("brandId") String brandId);
}
