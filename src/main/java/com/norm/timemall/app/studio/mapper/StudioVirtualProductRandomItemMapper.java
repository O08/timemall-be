package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.VirtualProductRandomItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVrProductRandomItemListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (virtual_product_random_item)数据Mapper
 *
 * @author kancy
 * @since 2025-09-15 14:49:07
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioVirtualProductRandomItemMapper extends BaseMapper<VirtualProductRandomItem> {

    ArrayList<StudioFetchVrProductRandomItemListRO> selectMerchandiseByProductId(@Param("productId") String productId,@Param("sellerBrandId") String sellerBrandId);

}
