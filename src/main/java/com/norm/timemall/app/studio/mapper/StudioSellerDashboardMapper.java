package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.SellerDashboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSellerDashBoardRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (seller_dashboard)数据Mapper
 *
 * @author kancy
 * @since 2025-06-18 14:52:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSellerDashboardMapper extends BaseMapper<SellerDashboard> {
    StudioFetchSellerDashBoardRO selectDashboardByBrand(@Param("seller_brand_id") String sellerBrandId);
}
