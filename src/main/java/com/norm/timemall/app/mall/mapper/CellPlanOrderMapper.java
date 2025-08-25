package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (cell_plan_order)数据Mapper
 *
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface CellPlanOrderMapper extends BaseMapper<CellPlanOrder> {
    @Update("update cell_plan_order set tag=#{tag} where id=#{id}")
    void updateTagById(@Param("tag") int tag, @Param("id") String orderId);
@Select("select o.consumer_id,c.brand_id from cell_plan_order o,cell c where o.cell_id=c.id and o.id=#{id}")
    CellPlanOrderRO selectCellPlanOrderROById(@Param("id") String orderId);
}
