package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.pod.domain.dto.PodGetProposalsPageDTO;
import com.norm.timemall.app.pod.domain.vo.PodGetProposalsPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PodProposalMapper extends BaseMapper<Proposal> {


    IPage<PodGetProposalsPageRO> selectPageByQ(Page<PodGetProposalsPageRO> page, @Param("dto") PodGetProposalsPageDTO dto,@Param("buyer_brand_id") String buyerBrandId);
}
