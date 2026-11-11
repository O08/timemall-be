package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 实物主订单表(physical_order)数据Mapper
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MallPodPhysicalOrderMapper extends BaseMapper<PhysicalOrder> {

    @Select("SELECT * FROM physical_order WHERE id = #{id} FOR UPDATE")
    PhysicalOrder selectForUpdate(@Param("id") String id);
}
