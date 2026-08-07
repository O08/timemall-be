package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.CellPlanDeliver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (cell_plan_deliver)数据Mapper
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCellPlanDeliverMapper extends BaseMapper<CellPlanDeliver> {


    ArrayList<FetchCellPlanOrderDeliverRO> selectPlanOrderDeliverByOrderIdAndBrandId(@Param("orderId") String orderId,@Param("brandId") String brandId);
}
