package com.norm.timemall.app.indicator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerVirtualProductIndicesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (virtual_product)数据Mapper
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface IndVirtualProductMapper extends BaseMapper<VirtualProduct> {

    void updateImpressions(@Param("dto") IndDataLayerVirtualProductIndicesDTO dto);

    void updateClicks(@Param("dto") IndDataLayerVirtualProductIndicesDTO dto);

    void updatePurchase(@Param("dto") IndDataLayerVirtualProductIndicesDTO dto);
}
