package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.CellPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.FetchCellPlanRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (cell_plan)数据Mapper
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CellPlanMapper extends BaseMapper<CellPlan> {

    ArrayList<FetchCellPlanRO> selectCellPlanByCellId(@Param("cellId") String cellId);
}
