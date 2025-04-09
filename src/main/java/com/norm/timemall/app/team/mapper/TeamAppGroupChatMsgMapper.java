package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppGroupChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFeedPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFetchMemberRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (app_group_chat_msg)数据Mapper
 *
 * @author kancy
 * @since 2025-04-08 10:02:02
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppGroupChatMsgMapper extends BaseMapper<AppGroupChatMsg> {

    IPage<TeamAppGroupChatFeedPageRO> selectFeedPage(IPage<TeamAppGroupChatFeedPageRO> page, @Param("channel") String channel);
    @Select("select b.avator avatar, b.brand_name ,b.id brandId from oasis_channel chn inner join oasis_member m on m.oasis_id=chn.oasis_id inner join brand b on b.id =  m.brand_id  where chn.id=#{channel} and locate(#{q}, b.brand_name)>0  order by m.modified_at desc limit 100")
    ArrayList<TeamAppGroupChatFetchMemberRO> selectListByChannel(@Param("channel") String channel,@Param("q") String q);
}
