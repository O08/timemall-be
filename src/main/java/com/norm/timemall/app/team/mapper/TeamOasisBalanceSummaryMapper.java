package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisBalanceSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisBalanceTrendRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (oasis_balance_summary)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisBalanceSummaryMapper extends BaseMapper<OasisBalanceSummary> {

    List<TeamFetchOasisBalanceTrendRO> selectBalanceTrendByOasisId(@Param("oasisId") String oasisId);
}
