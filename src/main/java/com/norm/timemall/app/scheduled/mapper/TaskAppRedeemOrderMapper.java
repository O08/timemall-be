package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.AppRedeemOrder;
import org.apache.ibatis.annotations.Mapper;


/**
 * (app_redeem_order)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskAppRedeemOrderMapper extends BaseMapper<AppRedeemOrder> {


    void callRefreshAppRedeemOrderDashboardProcedure();

}
