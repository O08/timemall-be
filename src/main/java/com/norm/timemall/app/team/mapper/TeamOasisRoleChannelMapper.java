package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisRoleChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamGetChannelOwnedByRoleRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (oasis_role_channel)数据Mapper
 *
 * @author kancy
 * @since 2025-08-23 11:24:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisRoleChannelMapper extends BaseMapper<OasisRoleChannel> {

    @Select("select chn.channel_name,chn.channel_desc from oasis_role_channel rc inner join oasis_channel chn on rc.oasis_channel_id=chn.id where rc.oasis_role_id=#{id} order by chn.create_at desc")
    ArrayList<TeamGetChannelOwnedByRoleRO> selectChannelByRoleId(@Param("id") String id);

}
