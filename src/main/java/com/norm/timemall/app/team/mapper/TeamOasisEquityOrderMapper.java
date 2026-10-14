package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.OasisEquityOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityOrderPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquitySponsorOrderPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorOrderPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (oasis_equity_order)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisEquityOrderMapper extends BaseMapper<OasisEquityOrder> {

    IPage<TeamFetchEquityOrderPageRO> selectPageByQ(Page<TeamFetchEquityOrderPageRO> page, @Param("dto") TeamFetchEquityOrderPageDTO dto);

    IPage<TeamFetchEquitySponsorOrderPageRO> selectSponsorPageByQ(Page<TeamFetchEquitySponsorOrderPageRO> page, @Param("dto") TeamFetchEquitySponsorOrderPageDTO dto);
}
