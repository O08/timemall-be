package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.CommissionDeliver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamDeliverLeaveMsgDTO;
import com.norm.timemall.app.team.domain.dto.TeamPutCommissionDeliverTagDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchCommissionDeliverRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (commission_deliver)数据Mapper
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamCommissionDeliverMapper extends BaseMapper<CommissionDeliver> {
    @Update("update commission_deliver set msg=#{dto.msg} where id=#{dto.deliverId} and commission_id=#{dto.commissionId}")
    void updateMsgById(@Param("dto") TeamDeliverLeaveMsgDTO dto);

    @Update("update commission_deliver set tag=#{dto.tag} where id=#{dto.deliverId} and commission_id=#{dto.commissionId}")
    void updateTagById(@Param("dto") TeamPutCommissionDeliverTagDTO dto);
    @Select("select id deliverId ,preview,preview_name,if(tag='3',deliver,'') deliver,if(tag='3',deliver_name,'') deliverName,msg,tag,commission_id  from commission_deliver where commission_id=#{commission_id} order  by create_at desc")
    ArrayList<TeamFetchCommissionDeliverRO> selectDeliverByCommissionId(@Param("commission_id") String commissionId);
    ArrayList<TeamFetchCommissionDeliverRO> selectDeliverByCommissionIdAndBrandId(@Param("commission_id") String commissionId, @Param("brandId") String brandId);
}
