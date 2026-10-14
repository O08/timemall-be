package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.Proposal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioFetchProposalPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchOneProposalRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchProposalPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (proposal)数据Mapper
 *
 * @author kancy
 * @since 2025-07-05 17:11:01
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioProposalMapper extends BaseMapper<Proposal> {

    IPage<StudioFetchProposalPageRO> selectPageByQ(Page<StudioFetchProposalPageRO> page, @Param("dto") StudioFetchProposalPageDTO dto,@Param("seller_brand_id") String sellerBrandId);

    StudioFetchOneProposalRO selectProposalByNo(@Param("project_no") String no);

}
