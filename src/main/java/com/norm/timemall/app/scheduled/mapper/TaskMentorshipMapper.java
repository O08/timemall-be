package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Mentorship;

import org.apache.ibatis.annotations.Mapper;


/**
 * (mentorship)数据Mapper
 *
 * @author kancy
 * @since 2026-03-01 11:38:42
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskMentorshipMapper extends BaseMapper<Mentorship> {
    void doExecuteRefreshTbDataProcedure();
}