package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.SellerDashboard;
import org.apache.ibatis.annotations.Mapper;


/**
 * (seller_dashboard)数据Mapper
 *
 * @author kancy
 * @since 2025-06-18 14:52:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskSellerDashboardMapper extends BaseMapper<SellerDashboard> {

    void refreshSellerDashboard();

}
