package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.SubsOffer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsOfferPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsGetShoppingOfferDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetOneSubsOfferRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsOfferPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioSubsGetShoppingOfferRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * (subs_offer)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSubsOfferMapper extends BaseMapper<SubsOffer> {

    IPage<StudioGetSubsOfferPageRO> selectPageByQ(Page<StudioGetSubsOfferPageRO> page, @Param("dto") StudioGetSubsOfferPageDTO dto, @Param("seller_brand_id") String sellerBrandId);
   @Select("select o.name,o.description,o.discount_amount,o.discount_percentage,o.for_product_id,o.for_plan_id,o.offer_type,o.status,o.claims,o.capacity,o.used from subs_offer o where o.promo_code=#{dto.promoCode} and o.seller_brand_id=#{dto.sellerBrandId}")
    StudioSubsGetShoppingOfferRO selectOfferForShopping(@Param("dto") StudioSubsGetShoppingOfferDTO dto);


}
