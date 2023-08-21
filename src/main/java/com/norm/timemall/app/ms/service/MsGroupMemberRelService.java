package com.norm.timemall.app.ms.service;

import org.springframework.stereotype.Service;

@Service
public interface MsGroupMemberRelService {
    void banOneUser(String channel, String userId);

    boolean beGroupMember(String channel);

    boolean haveReadAndWriteForChannel(String channel);
}