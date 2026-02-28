package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsProductPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsProductPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (subs_product)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSubsProductMapper extends BaseMapper<SubsProduct> {

    IPage<StudioGetSubsProductPageRO> selectPageByQ(Page<StudioGetSubsProductPageRO> page, @Param("dto")  StudioGetSubsProductPageDTO dto,@Param("seller_brand_id") String sellerBrandId);

}
