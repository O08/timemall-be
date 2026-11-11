package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppRedeemOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppRedeemGetAdminOrderPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppRedeemGetBuyerOrderPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemAdminGetOneOrderInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemBuyerGetOneOrderInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetAdminOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetBuyerOrderPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (app_redeem_order)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppRedeemOrderMapper extends BaseMapper<AppRedeemOrder> {

    IPage<TeamAppRedeemGetAdminOrderPageRO> selectAdminOrderPageByQ(IPage<TeamAppRedeemGetAdminOrderPageRO> page,@Param("seller_brand_id") String sellerBrandId,@Param("dto") TeamAppRedeemGetAdminOrderPageDTO dto);

    TeamAppRedeemAdminGetOneOrderInfoRO selectOneOrderForAdmin(@Param("seller_brand_id") String sellerBrandId, @Param("id") String id);

    IPage<TeamAppRedeemGetBuyerOrderPageRO> selectBuyerOrderPageByQ(IPage<TeamAppRedeemGetBuyerOrderPageRO> page,@Param("buyer_brand_id") String buyerBrandId,@Param("dto") TeamAppRedeemGetBuyerOrderPageDTO dto);

    TeamAppRedeemBuyerGetOneOrderInfoRO selectOneOrderForBuyer(@Param("buyer_brand_id") String buyerBrandId,@Param("id") String id);
}
