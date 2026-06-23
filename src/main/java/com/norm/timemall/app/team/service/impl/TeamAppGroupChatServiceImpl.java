package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppGroupChatMsg;
import com.norm.timemall.app.base.pojo.DefaultTextMessage;
import com.norm.timemall.app.team.domain.dto.TeamAppGroupChatStoreTextMessageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamAppGroupChatFetchMember;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFeedPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFetchMemberRO;
import com.norm.timemall.app.team.mapper.TeamAppGroupChatMsgMapper;
import com.norm.timemall.app.team.service.TeamAppGroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamAppGroupChatServiceImpl implements TeamAppGroupChatService {
    @Autowired
    private TeamAppGroupChatMsgMapper teamAppGroupChatMsgMapper;
    @Override
    public IPage<TeamAppGroupChatFeedPageRO> findFeeds(String channel, PageDTO dto) {

        IPage<TeamAppGroupChatFeedPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());

        return teamAppGroupChatMsgMapper.selectFeedPage(page,channel);

    }

    @Override
    public void doStoreTextMessage(TeamAppGroupChatStoreTextMessageDTO dto) {

        DefaultTextMessage textMessage = new DefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();

        AppGroupChatMsg msg = new AppGroupChatMsg();
        msg.setId(IdUtil.simpleUUID())
                .setOasisChannelId(dto.getChannel())
                .setQuoteMsgId(dto.getQuoteMsgId())
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppGroupChatMsgMapper.insert(msg);

    }

    @Override
    public void doStoreImageMessage(String channel, String msgJson, String msgType) {

        AppGroupChatMsg msg = new AppGroupChatMsg();
        msg.setId(IdUtil.simpleUUID())
                .setOasisChannelId(channel)
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppGroupChatMsgMapper.insert(msg);

    }

    @Override
    public void doStoreAttachmentMessage(String channel, String msgJson, String msgType) {

        AppGroupChatMsg msg = new AppGroupChatMsg();
        msg.setId(IdUtil.simpleUUID())
                .setOasisChannelId(channel)
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());


        teamAppGroupChatMsgMapper.insert(msg);
    }

    @Override
    public AppGroupChatMsg findOneFeed(String id) {
        return teamAppGroupChatMsgMapper.selectById(id);
    }

    @Override
    public void doRemoveMessage(String id) {
        teamAppGroupChatMsgMapper.deleteById(id);
    }

    @Override
    public TeamAppGroupChatFetchMember findMember(String channel,String q) {

        ArrayList<TeamAppGroupChatFetchMemberRO> records = teamAppGroupChatMsgMapper.selectListByChannel(channel,q);
        TeamAppGroupChatFetchMember member = new TeamAppGroupChatFetchMember();
        member.setRecords(records);
        return member;

    }

    @Override
    public void removeChannelData(String channel) {

        LambdaQueryWrapper<AppGroupChatMsg> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppGroupChatMsg::getOasisChannelId,channel);
        teamAppGroupChatMsgMapper.delete(wrapper);

    }
}
