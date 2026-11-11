package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchLinkMarketingPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchLinkMarketingPageRO;
import com.norm.timemall.app.base.mo.AffiliateLinkMarketing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通用分销表(affiliate_link_marketing)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateLinkMarketingMapper extends BaseMapper<AffiliateLinkMarketing> {

    IPage<FetchLinkMarketingPageRO> selectPageByDTO(IPage<FetchLinkMarketingPageRO> page, @Param("brandId") String brandId,@Param("dto") FetchLinkMarketingPageDTO dto);
}
