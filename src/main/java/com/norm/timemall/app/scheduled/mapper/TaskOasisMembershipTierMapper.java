package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.OasisMembershipTier;
import org.apache.ibatis.annotations.Mapper;

/**
 * (oasis_membership_tier)数据Mapper
 *
 * @author kancy
 * @since 2025-10-29 15:52:41
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskOasisMembershipTierMapper extends BaseMapper<OasisMembershipTier> {
    void callRefreshMembershipTierStatsInfoProcedure();
}
