package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.pojo.MsFetchPrivateFriend;
import org.springframework.stereotype.Service;

@Service
public interface MsPrivateRelService {
    MsFetchPrivateFriend findFriend();


    void markAllMsgAsRead(String friend);
}
