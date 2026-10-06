package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.BrandPromotion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.ro.FetchBrandPromotionRO;
import com.norm.timemall.app.studio.domain.ro.FetchCellCouponBenefitRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * (brand_promotion)数据Mapper
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioBrandPromotionMapper extends BaseMapper<BrandPromotion> {
@Select("select credit_point,credit_point_tag,credit_point_cnt,early_bird_discount,early_bird_discount_tag,early_bird_discount_cnt,repurchase_discount,repurchase_discount_tag,repurchase_discount_cnt from brand_promotion where brand_id=#{brandId}")
    FetchBrandPromotionRO selectPromotionByBrandId(@Param("brandId") String brandId);

    FetchCellCouponBenefitRO selectCouponBenefit(@Param("cellId") String id);

    BigDecimal selectCouponCreditPointBenefit(@Param("supplierBrandId") String supplierBrandId,
                                              @Param("consumerBrandId")String consumerBrandId);

}
