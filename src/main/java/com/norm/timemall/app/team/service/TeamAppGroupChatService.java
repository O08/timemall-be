package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.mo.AppGroupChatMsg;
import com.norm.timemall.app.team.domain.dto.TeamAppGroupChatStoreTextMessageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamAppGroupChatFetchMember;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFeedPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppGroupChatService {
    IPage<TeamAppGroupChatFeedPageRO> findFeeds(String channel, PageDTO dto);

    void doStoreTextMessage(TeamAppGroupChatStoreTextMessageDTO dto);

    void doStoreImageMessage(String channel, String msgJson, String msgType);

    void doStoreAttachmentMessage(String channel, String msgJson, String msgType);

    AppGroupChatMsg findOneFeed(String id);

    void doRemoveMessage(String id);

    TeamAppGroupChatFetchMember findMember(String channel);

    void removeChannelData(String id);
}
