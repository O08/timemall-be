package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppDeskTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppDeskGetElementsDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppDeskGetElementsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (app_desk_topic)数据Mapper
 *
 * @author kancy
 * @since 2025-06-11 17:48:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppDeskTopicMapper extends BaseMapper<AppDeskTopic> {

    ArrayList<TeamAppDeskGetElementsRO> selectElements(@Param("dto") TeamAppDeskGetElementsDTO dto);
@Update("update app_desk_topic set od=od+1 where oasis_channel_id=#{chn} and od=#{od}")
    void incrementOdByChnAndOd(@Param("chn") String oasisChannelId, @Param("od") Long od);
    @Update("update app_desk_topic set od=od-1 where oasis_channel_id=#{chn} and od=#{od}")
    void minusOdByChnAndOd(@Param("chn") String oasisChannelId, @Param("od") Long od);
    @Update("update app_desk_topic set od=od-1 where oasis_channel_id=#{chn} and od > #{od}")
    void reorderForBiggerThanOd(@Param("chn") String oasisChannelId, @Param("od") Long od);
}
