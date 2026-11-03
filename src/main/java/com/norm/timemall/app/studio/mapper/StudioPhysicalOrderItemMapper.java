package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 实物订单商品明细表(physical_order_item)数据Mapper
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioPhysicalOrderItemMapper extends BaseMapper<PhysicalOrderItem> {

    @Select("SELECT * FROM physical_order_item WHERE id = #{id} FOR UPDATE")
    PhysicalOrderItem selectForUpdate(@Param("id") String id);

}
