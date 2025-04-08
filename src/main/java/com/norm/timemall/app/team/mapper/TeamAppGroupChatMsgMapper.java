package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppGroupChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFeedPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
