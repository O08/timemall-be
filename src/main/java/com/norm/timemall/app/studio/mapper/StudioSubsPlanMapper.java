package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.SubsPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.studio.domain.dto.StudioGetShoppingSubscriptionMetaInfoDTO;
import com.norm.timemall.app.studio.domain.dto.StudioGetShoppingSubscriptionPlansDTO;
import com.norm.timemall.app.studio.domain.dto.StudioGetSpaceSubscriptionPlanPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsPlanPageDTO;
import com.norm.timemall.app.studio.domain.ro.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (subs_plan)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSubsPlanMapper extends BaseMapper<SubsPlan> {

    IPage<StudioGetSubsPlanPageRO> selectPageByQ(Page<StudioGetSubsPlanPageRO> page, @Param("dto") StudioGetSubsPlanPageDTO dto,@Param("seller_brand_id") String sellerBrandId);
@Select("select t.* from subs_product t inner join subs_plan p on p.product_id=t.id where p.id=#{plan_id}")
    SubsProduct selectProductByPlanId(@Param("plan_id") String planId);
    StudioGetOneSubsPlanRO selectOnePlanById(@Param("plan_id") String planId);

    IPage<StudioGetSpaceSubscriptionPlanPageRO> selectSpacePlans(IPage<StudioGetSpaceSubscriptionPlanPageRO> page,@Param("dto") StudioGetSpaceSubscriptionPlanPageDTO dto);

    ArrayList<StudioGetShoppingSubscriptionPlansRO> selectShoppingPlans(@Param("dto") StudioGetShoppingSubscriptionPlansDTO dto);

    StudioGetShoppingSubscriptionMetaInfoRO selectShoppingMeta(@Param("dto") StudioGetShoppingSubscriptionMetaInfoDTO dto);

}
