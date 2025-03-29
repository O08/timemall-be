package com.norm.timemall.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.ChannelTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.GroupMemberRel;
import com.norm.timemall.app.team.mapper.TeamGroupMemberRelMapper;
import com.norm.timemall.app.team.service.TeamGroupMemberRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamGroupMemberRelServiceImpl implements TeamGroupMemberRelService {
    @Autowired
    private TeamGroupMemberRelMapper teamGroupMemberRelMapper;
    @Override
    public void unfollowChannel(String oasisId) {

        LambdaQueryWrapper<GroupMemberRel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(GroupMemberRel::getChannelId,oasisId)
                        .eq(GroupMemberRel::getChannelType, ChannelTypeEnum.DEFAULT.getMark())
                                .eq(GroupMemberRel::getMemberId, SecurityUserHelper.getCurrentPrincipal().getUserId());

        teamGroupMemberRelMapper.delete(wrapper);

    }

    @Override
    public void removeMemberFromChannel(String oasisId,String memberUserId) {

        LambdaQueryWrapper<GroupMemberRel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(GroupMemberRel::getChannelId,oasisId)
                .eq(GroupMemberRel::getChannelType, ChannelTypeEnum.DEFAULT.getMark())
                .eq(GroupMemberRel::getMemberId, memberUserId);

        teamGroupMemberRelMapper.delete(wrapper);

    }
}
