package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisEquityPeriodSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamFetchLatestPeriodEquitySummaryRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (oasis_equity_period_summary)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisEquityPeriodSummaryMapper extends BaseMapper<OasisEquityPeriodSummary> {

    TeamFetchLatestPeriodEquitySummaryRO selectLatestPeriodSummaryByOasisId(@Param("oasisId") String oasisId);
}
