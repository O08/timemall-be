package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OfficeDashboard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchDashboardRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (office_dashboard)数据Mapper
 *
 * @author kancy
 * @since 2025-11-20 09:55:38
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeDashboardMapper extends BaseMapper<OfficeDashboard> {
@Select("select d.employees,d.pending_payments,d.monthly_expense,d.monthly_payrolls from office_dashboard d where d.oasis_id=#{oasisId}")
    TeamOfficeFetchDashboardRO selectDashboardByOasisId(@Param("oasisId") String oasisId);
}
