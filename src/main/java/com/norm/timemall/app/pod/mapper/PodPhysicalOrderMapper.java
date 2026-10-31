package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.pod.domain.dto.PodFetchPhysicalOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderDetailRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchPhysicalOrderPageRO;
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
public interface PodPhysicalOrderMapper extends BaseMapper<PhysicalOrder> {

    @Select("SELECT * FROM physical_order WHERE id = #{id} FOR UPDATE")
    PhysicalOrder selectForUpdate(@Param("id") String id);

    IPage<PodFetchPhysicalOrderPageRO> selectOrderPage(Page<PodFetchPhysicalOrderPageRO> page,
                                                       @Param("dto") PodFetchPhysicalOrderPageDTO dto,
                                                       @Param("buyerBrandId") String buyerBrandId);

    PodFetchPhysicalOrderDetailRO selectDetailByBrandAndId(@Param("orderId") String orderId,
                                                           @Param("buyerBrandId") String buyerBrandId);

}
