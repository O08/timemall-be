package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.pojo.MsFetchGroupMemberProfile;
import com.norm.timemall.app.team.domain.ro.TeamOasisFetchUserCtaInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface MsGroupMemberRelService {
    void banOneUser(String oasisId, String userId);

    boolean beGroupMember(String channel);

    boolean haveReadAndWriteForOasis(String oasisId);

    MsFetchGroupMemberProfile findOneMemberProfile(String channel, String memberUserId);

    void unbanOneUser(String oasisId, String userId);

    TeamOasisFetchUserCtaInfoRO findUserCtaInfo(String oasisId);
}
