package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsProductPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsProductPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (subs_product)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamSubsProductMapper extends BaseMapper<SubsProduct> {
  @Select("select t.* from subs_product t  inner join brand seller on t.seller_brand_id= seller.id where seller.handle=#{sellerHandle} and  t.product_code=#{productCode}")
  SubsProduct selectProductBySellerHandleAndProductCode(@Param("sellerHandle") String sellerHandle,@Param("productCode") String productCode);

}
