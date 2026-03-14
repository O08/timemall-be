package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.AppRedeemProductStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * (app_redeem_product_stats)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskAppRedeemProductStatsMapper extends BaseMapper<AppRedeemProductStats> {

@Update("update app_redeem_product_stats set month_buyer_orders=0")
    void resetMonthBuyerOrders();

}
