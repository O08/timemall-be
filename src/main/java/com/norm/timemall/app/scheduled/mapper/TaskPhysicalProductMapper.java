package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;


/**
 * 实物商品信息表(physical_product)数据Mapper
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskPhysicalProductMapper extends BaseMapper<PhysicalProduct> {
    /**
     * 逆向归还库存：订单取消订单时，原子将库存加回
     */
    @Update("""
        UPDATE physical_product SET inventory = inventory + #{quantity}, modified_at = #{now} WHERE id = #{productId}
    """)
    int restoreInventory(@Param("productId") String productId,
                         @Param("quantity") Integer quantity,
                         @Param("now") Date now);

}
