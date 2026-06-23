package com.norm.timemall.app.team.service;

import org.springframework.stereotype.Service;

@Service
public interface TeamGroupMemberRelService {
    void unfollowChannel(String oasisId);

    void removeMemberFromChannel(String oasisId,String memberUserId);
}
