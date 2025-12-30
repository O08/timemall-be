package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.pojo.MsFetchGroupMemberProfile;
import org.springframework.stereotype.Service;

@Service
public interface MsGroupMemberRelService {
    void banOneUser(String oasisId, String userId);

    boolean beGroupMember(String channel);

    boolean haveReadAndWriteForOasis(String oasisId);

    MsFetchGroupMemberProfile findOneMemberProfile(String channel, String memberUserId);

}
