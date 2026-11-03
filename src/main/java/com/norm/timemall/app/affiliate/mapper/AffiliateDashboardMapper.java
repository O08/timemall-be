package com.norm.timemall.app.affiliate.mapper;

import com.norm.timemall.app.affiliate.domain.ro.DashboardRO;
import com.norm.timemall.app.base.mo.AffiliateDashboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 带货看板表(affiliate_dashboard)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateDashboardMapper extends BaseMapper<AffiliateDashboard> {

    DashboardRO selectDashboardByBrandId(@Param("brandId") String brandId,@Param("timespan") String timespan);
}
