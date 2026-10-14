package com.norm.timemall.app.ms.service;

import org.springframework.stereotype.Service;

@Service
public interface MsGroupService {
    boolean beAdmin(String channel);
}
