package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.PhysicalProduct;
import com.norm.timemall.app.studio.domain.dto.StudioFetchPhysicalProductPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalProductPageRO;
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
public interface StudioPhysicalProductMapper extends BaseMapper<PhysicalProduct> {

    @Update("""
        UPDATE physical_product SET inventory = inventory + #{quantity}, modified_at = #{now} WHERE id = #{productId}
    """)
    int restoreInventory(@Param("productId") String productId,
                         @Param("quantity") Integer quantity,
                         @Param("now") Date now);

    IPage<StudioFetchPhysicalProductPageRO> selectPageByBrandAndQ(Page<StudioFetchPhysicalProductPageRO> page,
                                                                  @Param("brandId") String brandId,
                                                                  @Param("dto") StudioFetchPhysicalProductPageDTO dto);
}
