package com.norm.timemall.app.ms.service.impl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.ms.domain.pojo.MsFetchPrivateFriend;
import com.norm.timemall.app.ms.domain.ro.MsFetchPrivateFriendRO;
import com.norm.timemall.app.ms.mapper.MsPrivateRelMapper;
import com.norm.timemall.app.ms.service.MsPrivateRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MsPrivateRelServiceImpl implements MsPrivateRelService {
    @Autowired
    private MsPrivateRelMapper msPrivateRelMapper;

    @Override
    public MsFetchPrivateFriend findFriend() {

        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        ArrayList<MsFetchPrivateFriendRO> records = msPrivateRelMapper.selectPrivateFriendByUserId(userId);
        MsFetchPrivateFriend friend = new MsFetchPrivateFriend();
        friend.setRecords(records);
        return friend;

    }

    @Override
    public void markAllMsgAsRead(String friend) {

        String userId=SecurityUserHelper.getCurrentPrincipal().getUserId();
        msPrivateRelMapper.updateUnreadAsZeroById(userId,friend);

    }
}