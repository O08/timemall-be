package com.norm.timemall.app.ms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.ChannelTypeEnum;
import com.norm.timemall.app.base.enums.GroupMemberPolicyRelEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.GroupMemberRel;
import com.norm.timemall.app.ms.mapper.MsGroupMemberRelMapper;
import com.norm.timemall.app.ms.service.MsGroupMemberRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsGroupMemberRelServiceImpl implements MsGroupMemberRelService {
    @Autowired
    private MsGroupMemberRelMapper msGroupMemberRelMapper;
    @Override
    public void banOneUser(String channel, String userId) {

        msGroupMemberRelMapper.updatePolicyRelByChannelIdAndChannelTypeAndMemberId(channel, ChannelTypeEnum.DEFAULT.getMark(),
                userId, GroupMemberPolicyRelEnum.READ.getMark());

    }

    @Override
    public boolean beGroupMember(String channel) {

        LambdaQueryWrapper<GroupMemberRel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(GroupMemberRel::getChannelId,channel)
                        .eq(GroupMemberRel::getMemberId, SecurityUserHelper.getCurrentPrincipal().getUserId());
        return msGroupMemberRelMapper.exists(wrapper);

    }

    @Override
    public boolean haveReadAndWriteForChannel(String channel) {

        LambdaQueryWrapper<GroupMemberRel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(GroupMemberRel::getChannelId,channel)
                .eq(GroupMemberRel::getMemberId, SecurityUserHelper.getCurrentPrincipal().getUserId())
                .eq(GroupMemberRel::getPolicyRel,GroupMemberPolicyRelEnum.READ_WRITE.getMark());
        return msGroupMemberRelMapper.exists(wrapper);

    }
}
