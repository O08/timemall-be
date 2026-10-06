package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppEmbedWeb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamAppFetchEmbedWebChannelInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (app_embed_web)数据Mapper
 *
 * @author kancy
 * @since 2026-08-20 19:30:58
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppEmbedWebMapper extends BaseMapper<AppEmbedWeb> {

    @Select("select c.channel_name as channelName, c.channel_desc as channelDesc, e.web_uri as webUri " +
            "from oasis_channel c " +
            "left join app_embed_web e on e.oasis_channel_id = c.id " +
            "where c.id = #{channelId}")
    TeamAppFetchEmbedWebChannelInfoRO selectEmbedWebChannelInfo(@Param("channelId") String channelId);

}
