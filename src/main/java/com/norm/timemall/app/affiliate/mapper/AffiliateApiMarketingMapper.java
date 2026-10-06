package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchApiMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchApiMarketingPageRO;
import com.norm.timemall.app.base.mo.AffiliateApiMarketing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * API分销表(affiliate_api_marketing)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateApiMarketingMapper extends BaseMapper<AffiliateApiMarketing> {

    IPage<FetchApiMarketingPageRO> selectPageByDTO(IPage<FetchApiMarketingPageRO> page,@Param("brandId") String brandId,@Param("dto") FetchApiMarketingPageDTO dto);
}
