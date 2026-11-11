package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppRedeemDashboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetAdminOrderDashboardRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (app_redeem_dashboard)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppRedeemDashboardMapper extends BaseMapper<AppRedeemDashboard> {
@Select("select d.sold_orders,d.shipping_orders,d.buyers,d.total_sales from app_redeem_dashboard d where d.oasis_channel_id=#{channel}")
    TeamAppRedeemGetAdminOrderDashboardRO selectDashboardByChannel(@Param("channel") String channel);

}
