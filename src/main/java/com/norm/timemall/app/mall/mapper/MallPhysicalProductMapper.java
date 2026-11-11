package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import com.norm.timemall.app.mall.domain.dto.MallFetchPhysicalProductPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductInfoRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductPageRO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MallPhysicalProductMapper extends BaseMapper<PhysicalProduct> {

    IPage<MallFetchPhysicalProductPageRO> selectPageByBrandAndQ(Page<MallFetchPhysicalProductPageRO> page,
                                                                @Param("dto") MallFetchPhysicalProductPageDTO dto);

    MallFetchPhysicalProductInfoRO selectProfileInfo(@Param("productId") String productId);

    @Update("""
        UPDATE physical_product SET inventory = inventory - #{quantity}, modified_at = #{now} WHERE id = #{productId} AND inventory >= #{quantity}
    """)
    int deductInventory(@Param("productId") String productId,
                        @Param("quantity") Integer quantity,
                        @Param("now") Date now);

    @Update("""
        UPDATE physical_product SET sold_orders = sold_orders + #{quantity}, modified_at = #{now} WHERE id = #{productId}
    """)
    int incrementSalesVolume(@Param("productId") String productId,
                             @Param("quantity") Integer quantity,
                             @Param("now") Date now);
}
