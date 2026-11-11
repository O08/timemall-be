package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDeliveryInfoRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderPageRO;
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
public interface StudioPhysicalOrderMapper extends BaseMapper<PhysicalOrder> {

    @Select("SELECT * FROM physical_order WHERE id = #{id} FOR UPDATE")
    PhysicalOrder selectForUpdate(@Param("id") String id);

    IPage<StudioFetchPhysicalOrderPageRO> selectPageByBrandAndQ(Page<StudioFetchPhysicalOrderPageRO> page,
                                                                @Param("brandId") String brandId,
                                                                @Param("dto") StudioFetchPhysicalOrderPageDTO dto);

    StudioFetchPhysicalOrderDetailRO selectDetailByBrandAndId(@Param("orderId") String orderId,
                                                              @Param("brandId") String brandId);

    StudioFetchPhysicalOrderDeliveryInfoRO selectDeliveryInfoByBrandAndId(@Param("orderId") String orderId,
                                                                         @Param("brandId") String brandId);
}
