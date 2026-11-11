package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.OasisEquityPeriod;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityPeriodPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityPeriodPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (oasis_equity_period)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisEquityPeriodMapper extends BaseMapper<OasisEquityPeriod> {

    IPage<TeamFetchEquityPeriodPageRO> selectPageByQ(Page<TeamFetchEquityPeriodPageRO> page, @Param("dto") TeamFetchEquityPeriodPageDTO dto);


}
