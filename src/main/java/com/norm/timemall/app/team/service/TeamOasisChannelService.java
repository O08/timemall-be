package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.PutChannelGeneralDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisChannelService {
    ArrayList<FetchOasisChannelListRO> findOasisChannelList(String oasisId);

    void removeOasisChannel(String oasisChannelId);

    void modifyChannelGeneralInfo(PutChannelGeneralDTO dto);
}
