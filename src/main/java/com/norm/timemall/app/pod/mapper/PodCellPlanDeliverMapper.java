package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.CellPlanDeliver;
import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.pod.domain.dto.PodPutCellPlanDeliverTagDTO;
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
public interface PodCellPlanDeliverMapper extends BaseMapper<CellPlanDeliver> {

    @Select("select create_at, id deliverId ,preview,preview_name,if(tag='3',deliver,'') deliver,if(tag='3',deliver_name,'') deliverName,msg,tag  from cell_plan_deliver where plan_order_id=#{orderId} order  by create_at desc")
    ArrayList<FetchCellPlanOrderDeliverRO> selectCellPlanOrderDeliverByOrderId(@Param("orderId") String id);


    @Update("update cell_plan_deliver set msg=#{dto.msg} where id=#{dto.deliverId}")
    void updateMsgById(@Param("dto") DeliverLeaveMsgDTO dto);
    @Update("update cell_plan_deliver set tag=#{dto.tag} where id=#{dto.deliverId}")
    void updateTagById(@Param("dto") PodPutCellPlanDeliverTagDTO dto);
}
