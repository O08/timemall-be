package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.OfficePayroll;

import org.apache.ibatis.annotations.Mapper;


import java.util.Collection;

/**
 * (office_payroll)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskOfficePayrollMapper extends BaseMapper<OfficePayroll> {


    void calOasisPayrollStatsInfoProcedure();

}
