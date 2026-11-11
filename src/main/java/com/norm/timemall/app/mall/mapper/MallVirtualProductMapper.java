package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.dto.BrandGuideDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveBrandProductListPageDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveProductListPageDTO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.MallFetchVirtualProductProfileRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveBrandProductListPageRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveProductListPageRO;
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
public interface MallVirtualProductMapper extends BaseMapper<VirtualProduct> {

    IPage<MallRetrieveProductListPageRO> selectPageByQ(Page<MallRetrieveProductListPageRO> page, @Param("dto") MallRetrieveProductListPageDTO dto);

    IPage<MallRetrieveBrandProductListPageRO> selectPageByBrandAndQ(Page<MallRetrieveBrandProductListPageRO> page ,@Param("dto") MallRetrieveBrandProductListPageDTO dto);

    MallFetchVirtualProductProfileRO selectProfileInfo(@Param("productId") String id);

    MallHomeInfo selectHomeInfoByBrandIdOrHandle(@Param("dto") BrandGuideDTO dto);
}
