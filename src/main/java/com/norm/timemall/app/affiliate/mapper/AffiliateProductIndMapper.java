package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchProductGalleryPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchProductGalleryRO;
import com.norm.timemall.app.base.mo.AffiliateProductInd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 带货产品基表(affiliate_product_ind)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateProductIndMapper extends BaseMapper<AffiliateProductInd> {

    IPage<FetchProductGalleryRO> selectPageByDTO(IPage<FetchProductGalleryRO> page,  @Param("dto") FetchProductGalleryPageDTO dto);
}
