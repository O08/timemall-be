package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.OasisBalanceSummary;
import org.apache.ibatis.annotations.Mapper;


/**
 * (oasis_balance_summary)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskOasisBalanceSummaryMapper extends BaseMapper<OasisBalanceSummary> {

    void doExecuteProcedure();
}
