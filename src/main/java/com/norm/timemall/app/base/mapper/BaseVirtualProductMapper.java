package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.VirtualProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * (virtual_product)数据Mapper
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseVirtualProductMapper extends BaseMapper<VirtualProduct> {

    @Update("update virtual_product set product_status=#{status} where seller_brand_id=#{sellerBrandId}")
    void updateProductStatusBySeller(@Param("sellerBrandId") String sellerBrandId, @Param("status") String productStatus);
}
