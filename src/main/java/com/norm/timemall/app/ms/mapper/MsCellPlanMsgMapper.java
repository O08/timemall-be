package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.CellPlanMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (cell_plan_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsCellPlanMsgMapper extends BaseMapper<CellPlanMsg> {

    MsDefaultEvent selectEventByOrderId(@Param("room") String room);
}
