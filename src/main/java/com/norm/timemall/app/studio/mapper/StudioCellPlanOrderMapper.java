package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioCellPlanOrderPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCellPlanOrderRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (cell_plan_order)数据Mapper
 *
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCellPlanOrderMapper extends BaseMapper<CellPlanOrder> {

    IPage<StudioCellPlanOrderPageRO> selectCellPlanOrderPage(IPage<StudioCellPlanOrderPageRO> page,@Param("brandId") String brandId,
                                                             @Param("dto") FetchCellPlanOrderPageDTO dto);
    StudioFetchCellPlanOrderRO selectCellPlanOrderById(@Param("id") String id);
}
